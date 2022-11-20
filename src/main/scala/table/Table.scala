package pl.machnikovsky.generator
package table

import generationUtil.{DbInsert, StreamUtils}

import cats.effect.IO
import fs2.io.file.Path
import org.scalacheck.Gen
import org.scalacheck.rng.Seed

import scala.collection.mutable.ListBuffer

trait Table[A] {

  final val tableDirectory: Path = Path("src/main/resources/sql/data/tmp")
  final lazy val filePath: Path = tableDirectory / s"$tableName.sql"
  lazy val inMemoryList: ListBuffer[A] = ListBuffer(generator.pureApply(Gen.Parameters.default, Seed.random()))
  val tableName: String
  val rowsToGenerate: Long = 100_000L
  val generator: Gen[A]

  implicit val dbInsert: DbInsert[A]

  def generateData: IO[Unit] = StreamUtils.generateData(this)
  def getRandomRow: A                    = inMemoryList.head
}
