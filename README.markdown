# TODO Study/Practice/Exercise List

## Akka

### akka-stream 

* Streaming from a file: Done
* Streaming from MySQL

###

## Scalaz

## Monix

# Tutorials/Documentation

## Streaming from a file in `src/test/resources/ages.csv`

File contents in `src/test/resources/ages.csv`:

```
name,age
Oscar,35
Bety,26
Alex,7
Joshua,4
```

```scala
import java.nio.file.{Path, Paths}

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import akka.testkit.TestKit
import akka.util.ByteString
import cats.syntax.option._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfterAll, FunSpecLike, Matchers}

import scala.collection.immutable
import scala.concurrent._
import scala.xml.XML

class Serv2100Test(_system: ActorSystem)
    extends TestKit(_system)
    with FunSpecLike
    with Matchers
    with BeforeAndAfterAll
    with ScalaFutures {

  def this() = this(ActorSystem("system"))

  override def afterAll(): Unit = shutdown(_system)

  override implicit def patienceConfig: PatienceConfig =
    PatienceConfig(timeout = Span(60, Seconds), interval = Span(500, Millis))

  implicit val materializer: ActorMaterializer = ActorMaterializer()
  //implicit val ec:           ExecutionContextExecutor = ExecutionContext.global

  def processNameAndAge(source: Source[(String, Int), NotUsed]): Future[Done] =
    source.map { case (name, age) => s"$name is $age years old" }.runForeach(println)

  describe("Xml obfuscation rules from [[vyze.experimental.obfuscation.all.xmlRules]]") {

    it("Should be able to print integers from basic stream") {
      val source: Source[Int, NotUsed] = Source(1 to 5)

      source.runForeach(i => println(i)) //(materializer)
    }

    it("Should be able to map/transform emitted values") {
      val data = List(
        "Oscar" -> 35,
        "Bety" -> 26,
        "Alex" -> 7,
        "Joshua" -> 4
      )

      val source: Source[(String, Int), NotUsed] = Source(data)
      processNameAndAge(source)
    }

    it("Should be able to read Oscar's family data from csv file") {
      val file = Paths.get("src", "test", "resources", "ages.csv")

      val x1: Source[ByteString, Future[IOResult]] =
        FileIO.fromPath(file)

      val x2: Source[ByteString, Future[IOResult]] =
        x1.via(
          Framing.delimiter(ByteString("\n"), maximumFrameLength = 256, allowTruncation = true))

      val x3: Source[String, Future[IOResult]] =
        x2.map(_.utf8String)

      val x4: Future[Done] = x3.runWith(Sink.foreach[String](println))
    }

    /*
      # The following query was used to generate `id_ssn_request.csv`
      # on April 29th, 2018
      # (from jdbc:mysql://atx-06-pbddwrpreprod-dwr.ncl.loc:3306/vyze_dwr )

      sql
      select
      q1.dead_letter_id,
      q1.clear_ssn,
      replace(
          replace(
              convert(
                  from_base64(
                      trim('\"' from json_extract(q1.decoded_payload, '$.request'))
                  ),
                  char
              ),
              '\r',
              ''
          ),
          '\n',
          ''
      ) request
      from
        (select
           dl.id                                                              dead_letter_id,
           substring_index(substring_index(error_message, '\'', -2), '\'', 1) clear_ssn,
           convert(from_base64(payload), char)                                decoded_payload
         from dead_letter dl
         where source = 'Correspondence'
               and date(message_timestamp) >= '2017-01-01'
               and status_code like '%DWR.400.6%'
               and dead_letter_status = 'Open'
               and error_message regexp
                   '^vyze\\.experimental\\.common\\.CodedException\: STATUS CODE\: DWR\\.400\\.0\\. The following validation error\\(s\\) occurred\:[[:space:]]STATUS CODE\: DWR\\.400\\.6\\. \'[0-9]{9}\' is not a valid encoded ssn\\.[[:space:]]+Expected format \\{ssn hash\\},\\{last four of ssn\\}\.$') q1;
     */
    it("should be able to obfuscate SSN in requests from src/test/resources/id_ssn_request.csv") {

      import com.roundeights.hasher.Implicits._
      import vyze.experimental.obfuscation.all._

      implicit class ExtraStringOps(s: String) {
        def strip(removeMe: String): String = s.stripPrefix(removeMe).stripSuffix(removeMe)
      }

      def encryptSsn(clearSsn: String): String = s"${clearSsn.sha256.hex},${clearSsn.takeRight(4)}"

      implicit class CsvOps(path: Path) {
        // Creates frames (of `ByteString`s) delimited by '\n'
        private def newLineSeparatedFraming: Flow[ByteString, ByteString, NotUsed] =
          Framing.delimiter(ByteString("\n"), maximumFrameLength = 5000, allowTruncation = true)

        private def byteStringToCommaSeparated(s: ByteString): Array[String] =
          s.utf8String.split(',')

        def readCsv(): Source[Array[String], Future[IOResult]] =
          FileIO
            .fromPath(path)
            .via(newLineSeparatedFraming)
            .map(byteStringToCommaSeparated)
      }

      implicit class ObfuscationOps(request: String) {
        /**
          * Apply xml obfuscation rules to [[request]]
          *
          * @note Configuration of CSV Formats used in IntelliJ to save query results in a CSV file.
          *       '''This is a precondition for the method to work correctly'''
          * - Preferences... > Tools > Database > CSV Formats
          * - `Formats:` section > Comma-separated (CSV)
          * - `Quotation` character: 😎. Escape: duplicate.
          *       😎 was used because it is very unlikely that the unicode character 😎 (U+1F60E)
          *       appears in the request text (and therefore has to be escaped).
          *       '''This actually simplifies the XML parsing with `XML.loadString(s)`'''
          * - `Quote values:` When needed
          * - `Trim whitespaces`: Enabled
          * - `Fist row is header`: Disabled
          * - `First column is header`: Disabled
          * @param rules
          * @param deadLetterId
          * @return obfuscated ssn
          */
        def obfuscateSsn(rules: XmlObfuscationRules, deadLetterId: String): String = {

          /** apply obfuscation rules to [[request]] */
          val obfuscatedXml =
            rules
              .run(XML.loadString(request.strip("😎")))
              .getOrElse(fail(s"No obfuscation rule applies for dead letter id: $deadLetterId"))

          // Extract obfuscated SSN
          (obfuscatedXml \\ "SSN").headOption
            .getOrElse(fail(s"SSN not provided in request for dead letter id: $deadLetterId"))
            .text
            .trim
        }
      }

      def badIdFromRowData(csvRow: Array[String]): Option[Int] =
        csvRow match {
          case Array(deadLetterId, clearSsn, request) =>
            val actualEncryptedSsn   = request.obfuscateSsn(xmlRules, deadLetterId)
            val expectedEncryptedSsn = encryptSsn(clearSsn)

            if (actualEncryptedSsn != expectedEncryptedSsn)
              deadLetterId.toInt.some // xml obfuscation rules failed for this dead letter id
            else
              none
          case _ => fail("Unexpected csv row format")
        }

      val path: Path = Paths.get("src", "test", "resources", "id_ssn_request.csv")

      type ID = Int

      val badIds: Source[ID, Future[IOResult]] =
        path
          .readCsv()
          .map(badIdFromRowData)
          .collect { case Some(id) => id }

      val col: Future[immutable.IndexedSeq[ID]] =
        badIds.runWith(Sink.collection)

      col.futureValue.isEmpty shouldBe true
    }
  }
}
```