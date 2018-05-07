package io.github.oscarvarto.scalazexercises

import scalaz._
import Scalaz._

object TypeclassUsageZ {
  // Eq typeclass usage
  val x1: Boolean = (1 -> 2) === (1 -> 2)
  val x2: Boolean = (1 -> 2) =/= (1 -> 1)
}
