package pl.machnikovsky.generator
package table.weronika

import generationUtil.Generation
import table.Table

import fs2.io.file.Path
import org.scalacheck.Gen

import java.time.LocalDateTime
import java.util.UUID

case class Return(
    returnId: UUID,
    date: LocalDateTime,
    numberOfItems: Int,
    purchaseId: UUID,
)

object Return extends Table[Return] {

  override val tableName: String   = "`return`"
  override lazy val filePath: Path = tableDirectory / "return_weronika.sql"
  override val generator: Gen[Return] = for {
    returnId      <- Generation.uuidGen
    date          <- Generation.timeFromGen(15)
    numberOfItems <- Generation.numFromTo(0, 50)
  } yield Return(returnId, date, numberOfItems, Purchase.getRandomRow.purchaseId)

  override def accessFields(ret: Return): Iterator[String] = ret.productElementNames
  override def accessValues(ret: Return): Iterator[Any]    = ret.productIterator
}
