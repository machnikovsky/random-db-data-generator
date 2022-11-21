package pl.machnikovsky.generator
package table

import generationUtil.Generation

import org.scalacheck.Gen

import java.util.UUID

case class CartOffer(
    cartOfferId: UUID,
    shoppingCartId: UUID,
    offerId: UUID,
    quantity: Int
)

object CartOffer extends Table[CartOffer] {

  override val tableName: String = "cart_offer"
  //override val rowsTogenerate: Long = 10_000L
  override val generator: Gen[CartOffer] = for {
    cartOfferId <- Generation.uuidGen
    quantity    <- Generation.numFromTo(0, 50)
  } yield CartOffer(cartOfferId, ShoppingCart.getRandomRow.shoppingCartId, Offer.getRandomRow.offerId, quantity)

  override def accessFields(cartOffer: CartOffer): Iterator[String] = cartOffer.productElementNames
  override def accessValues(cartOffer: CartOffer): Iterator[Any]    = cartOffer.productIterator
}
