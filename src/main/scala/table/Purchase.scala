package pl.machnikovsky.generator
package table

import generationUtil.Generation
import table.Purchase.PaymentMethod

import enumeratum.EnumEntry.Uppercase
import enumeratum.{Enum, EnumEntry}
import org.scalacheck.Gen

import java.time.LocalDateTime
import java.util.UUID

case class Purchase(
    purchaseId: UUID,
    accountId: UUID,
    offerId: UUID,
    quantity: Int,
    paymentMethod: PaymentMethod,
    date: LocalDateTime
)

object Purchase extends Table[Purchase] {

  sealed trait PaymentMethod extends EnumEntry with Uppercase
  object PaymentMethod extends Enum[PaymentMethod] {

    val values: IndexedSeq[PaymentMethod] = findValues
    final case object CREDIT extends PaymentMethod
    final case object CARD   extends PaymentMethod
  }

  override val tableName: String = "purchase"
  //override val rowsTogenerate: Long = 1_000_000L

  override val generator: Gen[Purchase] = for {
    purchaseId    <- Generation.uuidGen
    quantity      <- Generation.numFromTo(0, 50)
    paymentMethod <- Generation.enumGen(PaymentMethod)
    date          <- Generation.timeFromGen(15)
  } yield
    Purchase(purchaseId, Account.getRandomRow.accountId, Offer.getRandomRow.offerId, quantity, paymentMethod, date)

  override def accessFields(purchase: Purchase): Iterator[String] = purchase.productElementNames
  override def accessValues(purchase: Purchase): Iterator[Any]    = purchase.productIterator
}
