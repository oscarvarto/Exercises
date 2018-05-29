package io.github.oscarvarto

import scalaz.Equal
import scalaz.syntax.equal._

object EnumeratumUsage {
  import enumeratum._

  sealed trait DayOfWeek1 extends EnumEntry

  object DayOfWeek1 extends enumeratum.Enum[DayOfWeek1] {
    /*
     `findValues` is a protected method that invokes a macro to find all `Greeting` object declarations inside an `Enum`

     You use it to implement the `val values` member
    */
    val values = findValues

    case object Mon extends DayOfWeek1

    case object Tue extends DayOfWeek1

    case object Weds extends DayOfWeek1

    case object Thu extends DayOfWeek1

    case object Fri extends DayOfWeek1

    case object Sat extends DayOfWeek1

    case object Sun extends DayOfWeek1

    implicit val dayOfWeekEq: Equal[DayOfWeek1] = Equal.equalA[DayOfWeek1]
  }

  import DayOfWeek1._

  def isItFriday(d: DayOfWeek1): Boolean = d === Fri

  val x1: Boolean = isItFriday(DayOfWeek1.Mon)
}
