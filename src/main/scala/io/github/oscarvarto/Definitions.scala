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

import iota.Cop.Inject

object Definitions {

  type V = Cop[Int :: Double :: TNil]

  def decV[T: Decoder](implicit I : Inject[T, V]): Decoder[V] =
    (c: HCursor) => c.as[T].map(I.inj)
}

object Solution extends App {

  /*
  import Definitions._

  val x = decode[V]("1")(decV[Int])
  println(x)
  */


}
