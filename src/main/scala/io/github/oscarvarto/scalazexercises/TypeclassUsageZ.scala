package io.github.oscarvarto.scalazexercises

import scalaz._
import Scalaz._
import DayOfWeek._

object TypeclassUsageZ {
  // Eq typeclass usage
  val x1: Boolean = (1 -> 2) === (1 -> 2)

  val x2: Boolean = (1 -> 2) =/= (1 -> 1)

  val x3: Boolean = DayOfWeek.Mon === DayOfWeek.Fri

  val x4: IList[DayOfWeek] = DayOfWeek.Mon |-> DayOfWeek.Fri
}
