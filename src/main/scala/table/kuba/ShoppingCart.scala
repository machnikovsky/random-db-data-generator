package pl.machnikovsky.generator
package table.kuba

import generationUtil.Generation
import table.Table
import table.kuba.ShoppingCart.Stage

import enumeratum.EnumEntry.Uppercase
import enumeratum._
import org.scalacheck.Gen

import java.util.UUID

case class ShoppingCart(
    shoppingCartId: UUID,
    accountId: UUID,
    stage: Stage
)

object ShoppingCart extends Table[ShoppingCart] {

  sealed trait Stage extends EnumEntry with Uppercase
  object Stage extends Enum[Stage] {

    val values: IndexedSeq[Stage] = findValues

    final case object PUSTY      extends Stage
    final case object ZAPELNIONY extends Stage
    final case object PLATNOSC   extends Stage with Uppercase
  }

  override val tableName: String = "shopping_cart"
  //override val rowsTogenerate: Long = 1_000_000L
  override val generator: Gen[ShoppingCart] = for {
    shoppingCartId <- Generation.uuidGen
    stage          <- Generation.enumGen(Stage)
  } yield ShoppingCart(shoppingCartId, Account.getRandomRow.accountId, stage)

  override def accessFields(shoppingCart: ShoppingCart): Iterator[String] = shoppingCart.productElementNames
  override def accessValues(shoppingCart: ShoppingCart): Iterator[Any]    = shoppingCart.productIterator
}
