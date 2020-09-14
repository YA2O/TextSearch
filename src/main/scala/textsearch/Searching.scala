package textsearch

import textsearch.Model._

import scala.collection.parallel.CollectionConverters._

object Searching {

  final case class Hit(key: String, word: LowerCaseWord)

  def search(query: String, index: Index): Set[Ranking] = {
    val words: Set[LowerCaseWord] = WordTokenizing.extractWords(query)
    val hits: Set[Hit] = findHits(words, index)
    computeRankings(words, hits)
  }

  private def findHits(words: Set[LowerCaseWord], index: Index): Set[Hit] = {
    (
      for {
        (key, documentIndex) <- index.indices
        word <- words.par if documentIndex.words.contains(word)
      } yield { Hit(key, word) }
    ).toSet.seq
  }

  private def computeRankings(words: Set[LowerCaseWord], hits: Set[Hit]): Set[Ranking] = {
    val nrOfHitsPerLocation: Set[(String, Int)] = hits.groupMapReduce(_.key)(_ => 1)(_ + _).toSet
    nrOfHitsPerLocation.map {
      case (location: String, hitsNr: Int) =>
        Ranking(location, Math.round((hitsNr.toDouble / words.size) * 100).toInt)
    }
  }

}
