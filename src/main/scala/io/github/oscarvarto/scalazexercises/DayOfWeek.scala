package io.github.oscarvarto.scalazexercises

import java.{time => jt}
import scalaz._
import Scalaz._

object DOfWeek {

  class DayOfWeek private[DOfWeek](val jtDay: jt.DayOfWeek) extends AnyVal {
    override def toString: String = jtDay.name()
  }

  val Mon = new DayOfWeek(jt.DayOfWeek.MONDAY)

  val Tue = new DayOfWeek(jt.DayOfWeek.TUESDAY)

  val Weds = new DayOfWeek(jt.DayOfWeek.WEDNESDAY)

  val Thu = new DayOfWeek(jt.DayOfWeek.THURSDAY)

  val Fri = new DayOfWeek(jt.DayOfWeek.FRIDAY)

  val Sat = new DayOfWeek(jt.DayOfWeek.SATURDAY)

  val Sun = new DayOfWeek(jt.DayOfWeek.SUNDAY)

  implicit val dayOfWeekEnum: scalaz.Enum[DayOfWeek] = new scalaz.Enum[DayOfWeek] {
    override def succ(d: DayOfWeek): DayOfWeek = d match {
      case Mon => Tue
      case Tue => Weds
      case Weds => Thu
      case Thu => Fri
      case Fri => Sat
      case Sat => Sun
      case Sun => Mon
    }

    override def pred(d: DayOfWeek): DayOfWeek = d match {
      case Mon => Sun
      case Tue => Mon
      case Weds => Tue
      case Thu => Weds
      case Fri => Thu
      case Sat => Fri
      case Sun => Sat
    }

    def order(x: DayOfWeek, y: DayOfWeek): Ordering = Order[Int].order(x.jtDay.getValue, y.jtDay.getValue)

    override def succn(n: Int, a: DayOfWeek): DayOfWeek = super.succn(n % 7, a)

    override def predn(n: Int, a: DayOfWeek): DayOfWeek = super.predn(n % 7, a)

    override def min: Option[DayOfWeek] = Mon.some

    override def max: Option[DayOfWeek] = Sun.some
  }
}