## How to use

- Create a case class for each database table you want to generate data for. 
- Create companion object for each case class and make it extend ```Table[_]``` trait with itself as type parameter.
- Implement all the necessary fields/functions of ```Table[_]``` trait.
- In case of relationships between tables, make UUID of referenced as a field of class, not the whole class.
- In case of enums, create sealed trait with case objects extending it, as in example below.
- You define generator logic by overriding ```generator``` field from ```Table[_]``` trait. There are some predefined 'rules' in ```Generator``` class, but you'd likely want to adjust them to your needs.
- In ```Main```, only add case classes you created to ```tables``` list and run. SQL files will be generated in ```resources```.
- When overriding ```dbInsert``` in your tables case classes, don't forget about ```implicit``` keyword.
- There are already mine tables defined and list of them in ```Main```, I left them for reference as more examples, but you'd want to change ```tables``` in ```Main``` to your own tables.

## Example

```Scala
case class Offer(
                  offerId: UUID,
                  itemId: UUID,
                  publicationDate: PublicationDate,
                  shipmentType: ShipmentType,
                  offerType: OfferType
                )

object Offer extends Table[Offer] {

  final case class PublicationDate(value: LocalDateTime)

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

  override val tableName: String = "offer"
  //override val rowsToGenerate: Long = 200_000L
  override val generator: Gen[Offer] = for {
    offerId         <- Generation.uuidGen
    publicationDate <- Generation.publicationDateGen
    shipmentType    <- Generation.shipmentTypeGen
    offerType       <- Generation.offerTypeGen
  } yield Offer(offerId, Item.getRandomRow.itemId, publicationDate, shipmentType, offerType)

  override implicit val dbInsert: DbInsert[Offer] = (offer: Offer) =>
    s"insert into $tableName(offer_id, item_id, publication_date, shipment_type, offer_type) values ('${offer.offerId}', '${offer.itemId}', '${offer.publicationDate.value}', '${offer.shipmentType}', '${offer.offerType}');"

}
```