package pl.machnikovsky.generator
package table

import generationUtil.{ DbInsert, Generation }
import table.Offer.{ OfferType, PublicationDate, ShipmentType }

import fs2.io.file.Path
import org.scalacheck.Gen

import java.time.LocalDate
import java.util.UUID
import scala.collection.mutable.ListBuffer

case class Offer(
    offerId: UUID,
    itemId: UUID,
    publicationDate: PublicationDate,
    shipmentType: ShipmentType,
    offerType: OfferType
)

object Offer extends Table[Offer] {

  final case class PublicationDate(value: LocalDate)

  sealed trait OfferType
  object OfferType {
    final case object KUP_TERAZ  extends OfferType
    final case object LICYTACJA  extends OfferType
    final case object OGLOSZENIE extends OfferType
  }

  sealed trait ShipmentType
  object ShipmentType {
    final case object KURIER          extends ShipmentType
    final case object POCZTA          extends ShipmentType
    final case object PACZKOMAT       extends ShipmentType
    final case object ODBIOR_OSOBISTY extends ShipmentType
  }

  override val tableName: String               = "offer"
  override val filePath: Path                  = Path("src/main/resources/sql/data/tmp3/offer_tmp.sql")
  override val inMemoryList: ListBuffer[Offer] = ListBuffer()
  override val generator: Gen[Offer] = for {
    offerId         <- Generation.uuidGen
    publicationDate <- Generation.publicationDateGen
    shipmentType    <- Generation.shipmentTypeGen
    offerType       <- Generation.offerTypeGen
  } yield Offer(offerId, Item.getRandomRow.itemId, publicationDate, shipmentType, offerType)

  override implicit val dbInsert: DbInsert[Offer] = (offer: Offer) =>
    s"insert into $tableName(offer_id, item_id, publication_date, shipment_type, offer_type) values ('${offer.offerId}', '${offer.itemId}', '${offer.publicationDate.value}', '${offer.shipmentType}', '${offer.offerType}');"

}
