package pl.machnikovsky.generator
package table.kuba

import generationUtil.Generation
import table.Table
import table.kuba.Item.Category

import enumeratum.EnumEntry.Uppercase
import enumeratum._
import org.scalacheck.Gen

import java.util.UUID

final case class Item(itemId: UUID, name: String, description: String, category: Category, price: BigDecimal)

object Item extends Table[Item] {

  sealed trait Category extends EnumEntry with Uppercase
  object Category extends Enum[Category] {

    val values: IndexedSeq[Category] = findValues

    final case object OBUWIE        extends Category
    final case object UBRANIA       extends Category
    final case object SAMOCHODY     extends Category
    final case object NIERUCHOMOSCI extends Category
    final case object ZABAWKI       extends Category
    final case object INNE          extends Category

  }

  override val tableName = "item"
  //override val rowsToGenerate: Long = 200_000L
  override val generator: Gen[Item] =
    for {
      itemId      <- Generation.uuidGen
      name        <- Generation.stringOfNCharsGen(10)
      description <- Generation.stringOfNCharsGen(10)
      category    <- Generation.enumGen(Category)
      price       <- Generation.priceGen
    } yield Item(itemId, name, description, category, price)

  override def accessFields(item: Item): Iterator[String] = item.productElementNames
  override def accessValues(item: Item): Iterator[Any]    = item.productIterator
}
