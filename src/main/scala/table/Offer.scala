package pl.machnikovsky.generator
package table

import generationUtil.Generation
import table.Offer.{ OfferType, ShipmentType }

import enumeratum.EnumEntry.Uppercase
import enumeratum._
import org.scalacheck.Gen

import java.time.LocalDateTime
import java.util.UUID

case class Offer(
    offerId: UUID,
    itemId: UUID,
    publicationDate: LocalDateTime,
    shipmentType: ShipmentType,
    offerType: OfferType
)

object Offer extends Table[Offer] {

  final case class PublicationDate(value: LocalDateTime)

  sealed trait OfferType extends EnumEntry with Uppercase
  object OfferType extends Enum[OfferType] {

    val values: IndexedSeq[OfferType] = findValues

    final case object KUP_TERAZ  extends OfferType
    final case object LICYTACJA  extends OfferType
    final case object OGLOSZENIE extends OfferType
  }

  sealed trait ShipmentType extends EnumEntry with Uppercase
  object ShipmentType extends Enum[ShipmentType] {

    val values: IndexedSeq[ShipmentType] = findValues

    final case object KURIER          extends ShipmentType
    final case object POCZTA          extends ShipmentType
    final case object PACZKOMAT       extends ShipmentType
    final case object ODBIOR_OSOBISTY extends ShipmentType
  }

  override val tableName: String = "offer"
  //override val rowsTogenerate: Long = 200_000L
  override val generator: Gen[Offer] = for {
    offerId         <- Generation.uuidGen
    publicationDate <- Generation.timeFromGen(15)
    shipmentType    <- Generation.enumGen(ShipmentType)
    offerType       <- Generation.enumGen(OfferType)
  } yield Offer(offerId, Item.getRandomRow.itemId, publicationDate, shipmentType, offerType)

  override def accessFields(offer: Offer): Iterator[String] = offer.productElementNames
  override def accessValues(offer: Offer): Iterator[Any]    = offer.productIterator
}
