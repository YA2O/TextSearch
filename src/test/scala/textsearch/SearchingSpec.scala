package textsearch

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import textsearch.Model.DocumentIndex
import textsearch.Model.Index
import textsearch.Model.LowerCaseWord
import textsearch.Model.Ranking

import scala.collection.parallel.immutable.ParMap

class SearchingSpec extends AnyWordSpec with Matchers {

  "Searching words in document index" should {
    "return rankings" in {
      // Given
      val Some(word1) = LowerCaseWord("a")
      val Some(word2) = LowerCaseWord("b")
      val Some(word3) = LowerCaseWord("c")
      val index = Index(
        ParMap(
          "file1" -> DocumentIndex(Set(word1, word2, word3)),
          "file2" -> DocumentIndex(Set(word1)),
          "file3" -> DocumentIndex(Set(word1, word2)),
          "file4" -> DocumentIndex(Set()),
          "file5" -> DocumentIndex(Set(word1))
        )
      )
      // When
      val res = Searching.search(
        s"${word1.toString} ${word2.toString};${word2.toString},<>#@!../<>?~${word3.toString}",
        index
      )
      // Then
      val expected = Set(
        Ranking("file1", 100),
        Ranking("file2", 33),
        Ranking("file3", 67),
        Ranking("file5", 33)
      )
      res shouldBe expected
    }
  }
}
