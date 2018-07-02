package io.github.oscarvarto

import iota._
import TList.::
import TListK.:::
import io.circe.Decoder.Result
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.circe._
import io.circe.generic.semiauto._
import java.{util => ju}
import java.lang.{Math => jlm}

import cats.implicits._
import iota.Cop.Inject

object Definitions {

  type V = Cop[Int :: Double :: TNil]

  def decV[T: Decoder](implicit I : Inject[T, V]): Decoder[V] =
    (c: HCursor) => c.as[T].map(I.inj)

  def isAlphabetic(s: String): Boolean = s == s.sorted
    //ju.Arrays.equals(s.toArray.sorted, s.toArray)

  def loveVsFriendship(s: String): Int = {
    val m = ('a' to 'z').zip(1 to ('z' - 'a' + 1)).toMap[Char, Int]
    s.map(m(_)).sum
  }

  def log(complex: Array[Double]): Array[Double] = {
    val Array(x, y) = complex

    if (complex.forall(_ == 0.0))
      throw new ArithmeticException
    else {
      val re = jlm.log(jlm.sqrt(x*x + y*y))
      val im = jlm.atan2(y, x)
      Array(re, im)
    }
  }

  object MultiplesOf3Or5 {
    def solution(number: Int): Long = {
      val l3: Int = (number/3.0).ceil.toInt - 1
      val l5: Int = (number/5.0).ceil.toInt - 1
      val m3 = Set(1 to l3: _*).map(_ * 3L)
      val m5 = Set(1 to l5: _*).map(_ * 5L)
      (m3 ++ m5).sum
    }
  }

  def comp(seq1: Seq[Int], seq2: Seq[Int]): Boolean = {
    /*
    val b = for {
      s1 <- Option(seq1)
      s2 <- Option(seq2)
    } yield s1.sorted.map(n => n * n) == s2.sorted
    b.getOrElse(false)
    */

    // 5685ms
    if (seq1 != null && seq2 != null)
      seq1.sorted.map(n => n * n) == seq2.sorted
    else
      false
  }

  def testit(a: Int, b: Int): Int = a | b
}

object Solution extends App {

  /*
  import Definitions._

  val x = decode[V]("1")(decV[Int])
  println(x)

  val x: Option[Int] = 3.some
  val y: Option[Int] = none

  val z: Option[Int] = x |+| y
  println(z)
  */

  import io.github.oscarvarto.scalazexercises.Problem1Z._
  //println(x)
  println(res)
}
