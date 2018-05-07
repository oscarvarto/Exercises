package io.github.oscarvarto

import java.time.LocalDate
import org.scalameta.data.data


@data(copy = false, apply = false)
class Conference private (name: String, startDate: LocalDate, endDate: LocalDate)

object Conference {
  def apply(name: String, startDate: LocalDate, endDate: LocalDate): Option[Conference] =
    if (name.nonEmpty && (startDate.isEqual(endDate) || startDate.isBefore(endDate)))
      Some(new Conference(name, startDate, endDate))
    else
      None
}