package io.github.oscarvarto.catsexercises

import cats._
import cats.data._
import cats.implicits._
import cats.tests.CatsSuite
import TypeclassUsage._

class TypeclassUsageTests extends CatsSuite {

  test("Should support `Eq` typeclass operators for simple types") {
    x1 shouldBe true
    x2 shouldBe true
    x3 shouldBe true
    x4 shouldBe true
  }

}
