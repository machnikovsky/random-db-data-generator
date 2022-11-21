package pl.machnikovsky.generator
package table.weronika

import generationUtil.Generation
import table.Table
import table.weronika.Item.Category

import enumeratum.EnumEntry.Uppercase
import enumeratum._
import org.scalacheck.Gen

import java.util.UUID

final case class Item(itemId: UUID, name: String, category: Category, price: BigDecimal)

object Item extends Table[Item] {

  sealed trait Category extends EnumEntry with Uppercase
  object Category extends Enum[Category] {

    val values: IndexedSeq[Category] = findValues

    final case object DOM         extends Category
    final case object ELEKTRONIKA extends Category
    final case object JEDZENIE    extends Category
    final case object UBRANIA     extends Category
    final case object INNE        extends Category

  }

  override val tableName = "item"
  override val generator: Gen[Item] =
    for {
      itemId   <- Generation.uuidGen
      name     <- Generation.stringOfNCharsGen(10)
      category <- Generation.enumGen(Category)
      price    <- Generation.priceGen
    } yield Item(itemId, name, category, price)

  override def accessFields(item: Item): Iterator[String] = item.productElementNames
  override def accessValues(item: Item): Iterator[Any]    = item.productIterator
}
