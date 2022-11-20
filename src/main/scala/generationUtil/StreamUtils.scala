package pl.machnikovsky.generator
package generationUtil

import generationUtil.DbInsertSyntax.DbInsertOps

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
    in => in.evalMap(str => IO.delay(list.append(str)) >> IO.delay(str))

  def runAndCountTime(generateDataFunction: Long => IO[Unit], rows: Long, tableName: String): IO[Unit] =
    for {
      _         <- IO { println(s"Starting on ${Thread.currentThread().getName} - [$tableName]") }
      startTime <- IO.realTime
      _         <- generateDataFunction(rows)
      endTime   <- IO.realTime
//      _         <- IO { println(s"Time for generating $rows rows: ${(endTime - startTime).toSeconds} [$tableName]") }
      _ <- IO { println(s"Ending on ${Thread.currentThread().getName} - [$tableName]") }
    } yield ()

  def generateData[T: DbInsert](generator: Gen[T],
                                path: Path,
                                list: ListBuffer[T],
                                rows: Long,
                                tableName: String): IO[Unit] = {

    def accountGeneration(): IO[T] = IO(generator.pureApply(Gen.Parameters.default, Seed.random()))

    for {
      _ <- IO { println(s"Executing on ${Thread.currentThread().getName} - [$tableName]") }
      _ <- Files[IO].deleteIfExists(path)
      _ <- StreamUtils.createFileIfNotExists(path)
      _ <- Stream
        .repeatEval(accountGeneration())
        .take(rows)
        .through(StreamUtils.appendList(list))
        .map(account => account.toDbInsert)
        .intersperse("\n")
        .through(text.utf8.encode)
        .through(Files[IO].writeAll(path))
        .compile
        .drain
    } yield ()
  }
}
