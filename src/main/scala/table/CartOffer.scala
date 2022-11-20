package pl.machnikovsky.generator
package table

import generationUtil.{ DbInsert, Generation }

import fs2.io.file.Path
import org.scalacheck.Gen

import java.util.UUID
import scala.collection.mutable.ListBuffer

case class CartOffer(
    cartOfferId: UUID,
    shoppingCartId: UUID,
    offerId: UUID,
    quantity: Int
)

object CartOffer extends Table[CartOffer] {

  override val tableName: String                   = "cart_offer"
  override val filePath: Path                      = Path("src/main/resources/sql/data/tmp3/cart_offer_tmp.sql")
  override val inMemoryList: ListBuffer[CartOffer] = ListBuffer()
  override val generator: Gen[CartOffer] = for {
    cartOfferId <- Generation.uuidGen
    quantity    <- Gen.choose(0, 50)
  } yield CartOffer(cartOfferId, ShoppingCart.getRandomRow.shoppingCartId, Offer.getRandomRow.offerId, quantity)

  override implicit val dbInsert: DbInsert[CartOffer] = (cartOffer: CartOffer) =>
    s"insert into $tableName(cart_offer_id, shopping_cart_id, offer_id, quantity) values ('${cartOffer.cartOfferId}', '${cartOffer.shoppingCartId}', '${cartOffer.offerId}', '${cartOffer.quantity}');"

}
