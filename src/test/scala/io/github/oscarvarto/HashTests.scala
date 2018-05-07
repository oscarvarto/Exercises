package io.github.oscarvarto


import org.scalatest.{FlatSpec, Matchers}
import com.roundeights.hasher.Implicits._

class HashTests extends FlatSpec with Matchers {

  "sha256.hex of an empty string" should "be e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855" in {
    "".sha256.hex shouldBe "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
  }

  "sha256.hex of one space" should "be 36a9e7f1c95b82ffb99743e0c5c4ce95d83c9a430aac59f84ef3cbfab6145068" in {
    " ".sha256.hex shouldBe "36a9e7f1c95b82ffb99743e0c5c4ce95d83c9a430aac59f84ef3cbfab6145068"
  }

}
