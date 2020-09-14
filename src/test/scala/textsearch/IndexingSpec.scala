package textsearch

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import textsearch.Model.DocumentIndex

class IndexingSpec extends AnyWordSpec with Matchers {

  "Building document index" should {
    "extract all the words" in {
      // Given
      val line1 = "A, bb. CcC!"
      val line2 = "a; dd1 åæ"
      val line3 = "%@$% %$( (*&[ ]/."
      // When
      val res: DocumentIndex = Indexing.buildDocumentIndex(List(line1, line2, line3))
      // Then
      val expected = Set("a", "bb", "ccc", "dd", "åæ")
      res.words.map(_.toString) shouldBe expected
    }
  }

}
