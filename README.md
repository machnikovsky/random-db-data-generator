## How to use

- Create a case class for each database table you want to generate data for. 
- Create companion object for each case class and make it extend ```Table[_]``` trait with itself as type parameter.
- Implement all the necessary fields/functions of ```Table[_]``` trait.
- In case of relationships between tables, make UUID of referenced as a field of class, not the whole class. Use ```getRandomRow``` method of that class to get a random one.
- In case of enums, create sealed trait with case objects extending it, as in example below.
- You define generator logic by overriding ```generator``` field from ```Table[_]``` trait. There are some predefined 'rules' in ```Generator``` class, but you'd likely want to adjust them to your needs.
- In ```Main```, only add case classes you created to ```tables``` list and run. SQL files will be generated in ```resources```.
- There are already mine tables defined and list of them in ```Main```, I left them for reference as more examples, but you'd want to change ```tables``` in ```Main``` to your own tables.

## Example

```Scala
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

```