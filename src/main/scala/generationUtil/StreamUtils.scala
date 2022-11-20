package pl.machnikovsky.generator
package generationUtil

import generationUtil.DbInsertSyntax.DbInsertOps
import table.Table

import cats.effect.IO
import fs2.io.file.{ Files, Path }
import fs2.{ text, Pipe, Stream }
import org.scalacheck.Gen
import org.scalacheck.rng.Seed

import scala.collection.mutable.ListBuffer

object StreamUtils {
  def createFileIfNotExists(path: Path): IO[Unit] =
    Files[IO].exists(path).flatMap(exists => if (!exists) Files[IO].createFile(path) else IO.unit)

  def appendList[A](list: ListBuffer[A]): Pipe[IO, A, A] =
    in => in.evalMap(str => IO.delay(list.update(0, str)) >> IO.delay(str))

  def runAndCountTime(table: Table[_]): IO[Unit] =
    for {
      _         <- IO { println(s"Starting on ${Thread.currentThread().getName} - [${table.tableName}]") }
      startTime <- IO.realTime
      _         <- table.generateData
      endTime   <- IO.realTime
      _ <- IO {
        println(
          s"Time for generating ${table.rowsToGenerate} rows: ${(endTime - startTime).toSeconds}s [${table.tableName}]"
        )
      }
      _ <- IO { println(s"Ending on ${Thread.currentThread().getName} - [${table.tableName}]") }
    } yield ()

  def generateData[T: DbInsert](table: Table[T]): IO[Unit] = {

    def accountGeneration(): IO[T] = IO(table.generator.pureApply(Gen.Parameters.default, Seed.random()))

    for {
      _ <- IO {
        println(s"Executing on ${Thread.currentThread().getName} - [${table.tableName}]")
      }
      _ <- Files[IO].createDirectories(table.tableDirectory)
      _ <- Files[IO].deleteIfExists(table.filePath)
      _ <- StreamUtils.createFileIfNotExists(table.filePath)
      _ <- Stream
        .repeatEval(accountGeneration())
        .take(table.rowsToGenerate)
        .through(StreamUtils.appendList(table.inMemoryList))
        .map(account => account.toDbInsert)
        .intersperse("\n")
        .through(text.utf8.encode)
        .through(Files[IO].writeAll(table.filePath))
        .compile
        .drain
    } yield ()
  }

}
