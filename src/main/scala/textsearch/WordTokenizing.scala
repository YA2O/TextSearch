package textsearch

import Model._

object WordTokenizing {
  val nonAlphabeticCharacters = "[^\\p{IsAlphabetic}']+"

  /** Extract the distinct words from a string, all as lower-case */
  def extractWords(str: String): Set[LowerCaseWord] = {
    str.split(nonAlphabeticCharacters).flatMap(LowerCaseWord.apply).toSet
  }
}
