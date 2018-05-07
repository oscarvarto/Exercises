package io.github.oscarvarto.catsexercises

import cats._
import cats.data._
import cats.implicits._

object TypeclassUsage {

  // Eq typeclass usage
  val x1: Boolean = 4 === 2 + 2
  val x2: Boolean = 5 =!= 2 + 2
  val x3: Boolean = (1 -> 2) === (1 -> 2)
  val x4: Boolean = (1 -> 2) =!= (1 -> 1)
}
