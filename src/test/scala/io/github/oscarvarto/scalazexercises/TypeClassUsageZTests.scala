package io.github.oscarvarto.scalazexercises

import org.scalatest.{FunSpec, Matchers}
import TypeclassUsageZ._

class TypeClassUsageZTests extends FunSpec with Matchers {

  describe("Typeclasses with Scalaz") {
    it("Should support `Equal` typeclass operators") {
      x1 shouldBe true
      x2 shouldBe true
      x3 shouldBe false
    }
  }

}
