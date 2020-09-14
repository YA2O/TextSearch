package textsearch

import scala.collection.parallel.immutable.ParMap

object Model {

  final case class Ranking(key: String, score: Int)

  final case class Index(indices: ParMap[String, DocumentIndex])

  final case class DocumentIndex(words: Set[LowerCaseWord])

  final class LowerCaseWord private (override val toString: String) extends AnyVal

  object LowerCaseWord {
    private val alphabeticCharacters = "^[\\p{IsAlphabetic}']+$"

    def apply(str: String): Option[LowerCaseWord] = {
      if (isValid(str)) Some(unsafeApply(str))
      else None
    }

    private def isValid(str: String): Boolean = str.matches(alphabeticCharacters)

    private def unsafeApply(str: String): LowerCaseWord = new LowerCaseWord(str.toLowerCase)
  }

}
