package pl.machnikovsky.generator
package table

import generationUtil.DbInsertSyntax.DbInsertOps
import generationUtil.{DbInsert, StreamUtils}
import table.Table.{camelToSnake, toCamelIfNotEnum}

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import enumeratum.EnumEntry
import fs2.io.file.{Files, Path}
import fs2.text
import org.scalacheck.Gen
import org.scalacheck.rng.Seed

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.math.BigDecimal.RoundingMode

trait Table[A] {

  final lazy val suffix: String = "weronika"
  lazy val tableDirectory: Path = Path(s"src/main/resources/sql/data/$suffix")
  lazy val filePath: Path = tableDirectory / s"${tableName}_$suffix.sql"
  lazy val inMemoryList: ListBuffer[A] = {
    val lb = ListBuffer(generator.pureApply(Gen.Parameters.default, Seed.random()))
    (for {
      _ <- Files[IO].createDirectories(tableDirectory)
      _ <- Files[IO].deleteIfExists(filePath)
      _ <- StreamUtils.createFileIfNotExists(filePath)
      _ <- fs2.Stream
        .emits(lb)
        .head
        .map(account => account.toDbInsert)
        .intersperse("\n")
        .through(text.utf8.encode)
        .through(Files[IO].writeAll(filePath))
        .compile
        .drain
    } yield ()).unsafeRunSync()
    lb
  }

  val tableName: String
  val rowsToGenerate: Long = 10_000L
  val generator: Gen[A]

  implicit val dbInsert: DbInsert[A] = (a: A) =>
    s"insert into $tableName(${accessFields(a).toList.map(camelToSnake).mkString(", ")}) " +
      s"values (${accessValues(a).toList.map(toCamelIfNotEnum).mkString(", ")});"

  def accessFields(a: A): Iterator[String]

  def accessValues(a: A): Iterator[Any]

  def generateData: IO[Unit] = StreamUtils.generateData(this)

  def getRandomRow: A = inMemoryList.head
}

object Table {

  import java.time.LocalDateTime
  import java.time.format.DateTimeFormatter

  val fmt: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss")
  def toCamelIfNotEnum(field: Any): String = field match {
    case n: EnumEntry => s"'${n.toString}'"
    case n: LocalDateTime => s"'${n.format(fmt)}'"
    case n: BigDecimal => s"'${n.setScale(2, RoundingMode.CEILING)}'"
    case n => camelToSnake(s"'${camelToSnake(n.toString)}'")
  }
  def camelToSnake(name: String): String = {
    @tailrec
    def go(accDone: List[Char], acc: List[Char]): List[Char] = acc match {
      case Nil => accDone
      case a :: b :: c :: tail if a.isUpper && b.isUpper && c.isLower => go(accDone ++ List(a, '_', b, c), tail)
      case a :: b :: tail if a.isLower && b.isUpper => go(accDone ++ List(a, '_', b), tail)
      case a :: tail => go(accDone :+ a, tail)
    }

    go(Nil, name.toList).mkString.toLowerCase
  }
}
