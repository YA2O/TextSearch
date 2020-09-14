package textsearch

import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.wordspec.AnyWordSpec
import textsearch.Model.LowerCaseWord

class LowerCaseWordSpec extends AnyWordSpec with TableDrivenPropertyChecks with Matchers {

  "LowerCaseWord's constructor" should {
    "create words for alphabetic inputs" in {
      val alphabeticStrings = Table(
        ("input", "expected"),
        ("a", "a"),
        ("A", "a"),
        ("AA", "aa"),
        ("SEåäöÅÄÖ", "seåäöåäö"),
        ("âàæçéêèëîïôœûùüÿÂÀÆÇÉÊÈËÎÏÔŒÛÙÜŸ", "âàæçéêèëîïôœûùüÿâàæçéêèëîïôœûùüÿ"), // French accented letters
        ("áÁéÉíÍóÓúÚñÑüÜ", "ááééííóóúúññüü") // Spanish accented letters
      )
      forAll(alphabeticStrings) { (input, expected) =>
        LowerCaseWord(input).map(_.toString) shouldBe Some(expected)
      }
    }

    "not create words for non-alphabetic inputs" in {
      val nonAlphabeticStrings = Table(
        "input",
        "a!",
        "b ",
        ""
      )
      forAll(nonAlphabeticStrings) { input =>
        LowerCaseWord(input).map(_.toString) shouldBe None
      }
    }
  }
}
