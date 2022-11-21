package pl.machnikovsky.generator
package table.weronika

import generationUtil.Generation
import table.Table
import table.kuba.Item
import table.weronika.Offer.Status

import enumeratum.EnumEntry.Uppercase
import enumeratum.{Enum, EnumEntry}
import org.scalacheck.Gen

import java.util.UUID

case class Offer(
                  offerId: UUID,
                  sellerId: UUID,
                  description: String,
                  status: Status,
                  price: BigDecimal,
                  itemId: UUID
                )

object Offer extends Table[Offer] {

  sealed trait Status extends EnumEntry with Uppercase

  object Status extends Enum[Status] {

    val values: IndexedSeq[Status] = findValues

    final case object ACTIVE extends Status

    final case object SOLD_OUT extends Status
  }

  override val tableName: String = "offer"
  override val generator: Gen[Offer] = for {
    offerId <- Generation.uuidGen
    description <- Generation.stringOfNCharsGen(100)
    status <- Generation.enumGen(Status)
    price <- Generation.priceGen
  } yield Offer(offerId, User.getRandomRow.userId, description, status, price, Item.getRandomRow.itemId)

  override def accessFields(offer: Offer): Iterator[String] = offer.productElementNames

  override def accessValues(offer: Offer): Iterator[Any] = offer.productIterator
}
