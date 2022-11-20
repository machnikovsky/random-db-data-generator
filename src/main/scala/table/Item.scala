package pl.machnikovsky.generator
package table

import generationUtil.{ DbInsert, Generation }
import table.Item.Category

import fs2.io.file.Path
import org.scalacheck.Gen

import java.util.UUID
import scala.collection.mutable.ListBuffer

final case class Item(itemId: UUID, name: String, description: String, category: Category, price: BigDecimal)

object Item extends Table[Item] {

  sealed trait Category
  object Category {
    final case object OBUWIE        extends Category
    final case object UBRANIA       extends Category
    final case object SAMOCHODY     extends Category
    final case object NIERUCHOMOSCI extends Category
    final case object ZABAWKI       extends Category
    final case object INNE          extends Category
  }

  override val tableName                      = "item"
  override val filePath: Path                 = Path("src/main/resources/sql/data/tmp3/item_tmp.sql")
  override val inMemoryList: ListBuffer[Item] = ListBuffer()
  override val generator: Gen[Item] =
    for {
      itemId      <- Generation.uuidGen
      name        <- Gen.stringOfN(10, Gen.alphaChar)
      description <- Gen.stringOfN(10, Gen.alphaChar)
      category    <- Generation.categoryGen
      price       <- Generation.priceGen
    } yield Item(itemId, name, description, category, price)

  override implicit val dbInsert: DbInsert[Item] = (item: Item) =>
    s"insert into $tableName(item_id, name, description, category, price) values ('${item.itemId}', '${item.name}', '${item.description}', '${item.category}', '${item.price}');"

}
