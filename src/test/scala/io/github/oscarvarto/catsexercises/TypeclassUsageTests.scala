package io.github.oscarvarto.catsexercises

import cats._
import cats.data._
import cats.implicits._
import cats.tests.CatsSuite

class TypeclassUsageTests extends CatsSuite {

  test("Should support `Eq` typeclass operators for simple types") {
    //Eq.eqv(4, 2 + 2) shouldBe true
    val x: Boolean = 4 === 2 + 2
    x shouldBe true
  }
}
