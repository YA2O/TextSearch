package textsearch

import textsearch.Model._

import java.io.File
import scala.io.StdIn
import scala.util.Failure
import scala.util.Success
import scala.util.Try

object SimpleSearch extends App {

  (
    for {
      dir <- Program.readDirectory(args)
      index <- Indexing.buildIndex(dir)
    } yield Program.run(index)
  ).left.map(println)

}

object Program {

  sealed trait ReadFileError
  case object MissingPathArg extends ReadFileError {
    override val toString: String = "Missing directory path argument"
  }
  case class NotDirectory(path: String) extends ReadFileError {
    override val toString: String = s"File [$path] is not a directory"
  }
  case class FileNotFound(path: String, t: Throwable) extends ReadFileError {
    override val toString: String = s"File [$path] not found: [${t.getMessage}]"
  }

  def readDirectory(args: Array[String]): Either[ReadFileError, File] = {
    for {
      path <- args.headOption.toRight(MissingPathArg)
      file <- Try(new File(path)) match {
        case Failure(throwable) => Left(FileNotFound(path, throwable))
        case Success(file) =>
          if (file.isDirectory) Right(file)
          else Left(NotDirectory(path))
      }
    } yield file
  }

  def run(index: Index): Unit = {
    while (true) {
      print(s"search> ")
      val searchString = StdIn.readLine()
      val rankings = Searching.search(searchString, index)
      printRankings(rankings.toList)
    }
  }

  def printRankings(rankings: List[Ranking]): Unit = {
    if (rankings.isEmpty) println("no match found")
    else
      rankings
        .sortBy(_.score)
        .reverse
        .take(10)
        .foreach { ranking =>
          println(s"    ${ranking.key} : ${ranking.score}%")
        }

  }
}
