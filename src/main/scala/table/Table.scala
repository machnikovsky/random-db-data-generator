package pl.machnikovsky.generator
package table

import generationUtil.{ DbInsert, StreamUtils }

import cats.effect.IO
import fs2.io.file.Path
import org.scalacheck.Gen

import scala.collection.mutable.ListBuffer
import scala.util.Random

trait Table[A] {

  val tableName: String
  val filePath: Path
  val inMemoryList: ListBuffer[A]
  // TODO: Temporary solution, it kinda sucks that Gen can depend on list that could be empty, but maybe i'll figure something out in the future
  val generator: Gen[A]
  // TODO: figure out how to make it work
  // def generator(dependants: Any*): Gen[A]

  implicit val dbInsert: DbInsert[A]

  def generateData(rows: Long): IO[Unit] = StreamUtils.generateData(generator, filePath, inMemoryList, rows, tableName)
  def getRandomRow: A                    = inMemoryList(Random.nextInt(inMemoryList.length))
}
