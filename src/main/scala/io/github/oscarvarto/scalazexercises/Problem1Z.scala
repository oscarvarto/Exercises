package io.github.oscarvarto.scalazexercises

import scala.annotation.tailrec
import scala.util.Try
import scalaz._
import Scalaz._

object Problem1Z {

  implicit class ListExtraOps[A](as: List[A]) {
    def interleave(that: List[A]): List[A] = {
      @tailrec def loop(xs: List[A], ys: List[A], acc: List[A]): List[A] = xs match {
        case h :: t =>
          loop(ys, t, h :: acc)
        case Nil    =>
          acc reverse_::: ys
      }

      loop(as, that, List.empty[A])
    }
  }

  implicit class StringExtraOps(s: String) {
    //def toIList: IList[Char] = IList.fromList(s.toList)
    def interleave(that: String): String =
      StringBuilder.newBuilder.appendAll(s.toList interleave that.toList).result

    def asDouble: Option[Double] = Try(s.toDouble).toOption
  }

  var first : String = "abcdef"
  var second: String = "1234"

  //val x: String = StringBuilder.newBuilder.appendAll(first.toIList.interleave(second.toIList).toList).result
  val y: String = first.interleave(second)

  def interpolate(y0: Double, y1: Double): Double = y0 + (y1 - y0) / 2

  val AtLeast3ErrorMsg       = "At least 3 elements are needed to perform the operation."
  val FailedInterpolationMsg = "Failed to perform interpolation operation."

  val data                                   = Vector("1", "x", "3", "4")
  val n                                      = data.length
  val ds            : Vector[Option[Double]] = data.map(_.asDouble)
  val missingIndices: Vector[Int]            =
    ds.zipWithIndex.collect { case (None, i) => i }


  val res =
    if (n <= 3) {
      Left(AtLeast3ErrorMsg)
    } else if (missingIndices.contains(0) ||
               missingIndices.contains(n - 1) ||
               ds.slice(1, n - 1).sliding(2).exists { case Vector(None, None) => true; case _ => false } ) {
      Left(FailedInterpolationMsg)
    } else {
      val dArray = ds.toArray
      missingIndices.foreach { i =>
        dArray(i) = for {
          y0 <- dArray(i - 1)
          y1 <- dArray (i + 1)
        } yield interpolate(y0, y1)
      }

      dArray.toList.sequence
    }
}
