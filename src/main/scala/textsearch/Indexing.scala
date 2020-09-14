package textsearch

import textsearch.Model._

import java.io.File
import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.immutable.ParMap
import scala.io.Codec
import scala.io.Source
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import scala.util.Using

object Indexing {

  sealed trait IndexingError
  case class CanNotReadDirectory(dirName: String, t: Throwable) extends IndexingError {
    override val toString: String = s"Can not read directory [$dirName]: ${t.getMessage}."
  }

  /** Build an index for all files in this directory (not transitive: it will ignore files in
    * sub-directories). Note: this will only work for files with UTF-8 encoding.
    */
  def buildIndex(directory: File): Either[IndexingError, Index] = {
    Try(directory.listFiles().toSet) match {
      case Failure(throwable) => Left(CanNotReadDirectory(directory.getName, throwable))
      case Success(files) => {
        val indices: ParMap[String, DocumentIndex] = buildFileIndex(files)
        Right(Index(indices))
      }
    }
  }

  private def buildFileIndex(files: Set[File]): ParMap[String, DocumentIndex] = {
    val EmptyIndex = DocumentIndex(Set.empty)
    files.toList.par
      .map { file =>
        if (file.isDirectory)
          file.getName -> EmptyIndex
        else {
          Using(Source.fromFile(file)(Codec.UTF8)) { source =>
            buildDocumentIndex(source.getLines().toList)
          } match {
            case Failure(error) => {
              println(s" Error indexing [${file.getName}]: [$error]")
              file.getName -> EmptyIndex
            }
            case Success(index) => file.getName -> index
          }
        }
      }
      .filter { case (_, index) => index.words.nonEmpty }
      .toMap
  }

  def buildDocumentIndex(lines: List[String]): DocumentIndex = {
    val words = lines.flatMap(WordTokenizing.extractWords)
    DocumentIndex(words.toSet)
  }

}
