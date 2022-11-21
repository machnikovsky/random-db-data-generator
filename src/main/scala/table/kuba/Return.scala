package pl.machnikovsky.generator
package table.kuba

import generationUtil.Generation
import table.Table

import org.scalacheck.Gen

import java.time.LocalDateTime
import java.util.UUID

case class Return(
    returnId: UUID,
    accountId: UUID,
    offerId: UUID,
    quantity: Int,
    date: LocalDateTime
)

object Return extends Table[Return] {

  override val tableName: String = "return"
  //override val rowsTogenerate: Long = 20_000L
  override val generator: Gen[Return] = for {
    returnId <- Generation.uuidGen
    quantity <- Generation.numFromTo(0, 50)
    date     <- Generation.timeFromGen(15)
  } yield Return(returnId, Account.getRandomRow.accountId, Offer.getRandomRow.offerId, quantity, date)

  override def accessFields(ret: Return): Iterator[String] = ret.productElementNames
  override def accessValues(ret: Return): Iterator[Any]    = ret.productIterator
}
