package pl.machnikovsky.generator
package table

import generationUtil.{ DbInsert, Generation }
import table.ShoppingCart.Stage

import fs2.io.file.Path
import org.scalacheck.Gen

import java.util.UUID
import scala.collection.mutable.ListBuffer

case class ShoppingCart(
    shoppingCartId: UUID,
    accountId: UUID,
    stage: Stage
)

object ShoppingCart extends Table[ShoppingCart] {

  sealed trait Stage
  object Stage {
    final case object PUSTY      extends Stage
    final case object ZAPELNIONY extends Stage
    final case object PLATNOSC   extends Stage
  }

  override val tableName: String                      = "shopping_cart"
  override val filePath: Path                         = Path("src/main/resources/sql/data/tmp3/shopping_cart_tmp.sql")
  override val inMemoryList: ListBuffer[ShoppingCart] = ListBuffer()
  override val generator: Gen[ShoppingCart] = for {
    shoppingCartId <- Generation.uuidGen
    stage          <- Generation.stageGen
  } yield ShoppingCart(shoppingCartId, Account.getRandomRow.accountId, stage)

  override implicit val dbInsert: DbInsert[ShoppingCart] = (shoppingCart: ShoppingCart) =>
    s"insert into $tableName(shopping_cart_id, account_id, stage) values ('${shoppingCart.shoppingCartId}', '${shoppingCart.accountId}', '${shoppingCart.stage}');"

}
