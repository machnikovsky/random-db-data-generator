package pl.machnikovsky.generator
package table.weronika

import generationUtil.Generation
import table.Table
import table.weronika.Purchase.{ PaymentMethod, PurchaseStatus, ShippingMethod }

import enumeratum.EnumEntry.Uppercase
import enumeratum.{ Enum, EnumEntry }
import org.scalacheck.Gen

import java.time.LocalDateTime
import java.util.UUID

case class Purchase(
    purchaseId: UUID,
    paymentMethod: PaymentMethod,
    date: LocalDateTime,
    shippingMethod: ShippingMethod,
    purchaseStatus: PurchaseStatus,
    buyerId: UUID,
    sellerId: UUID,
    addressId: UUID,
)

object Purchase extends Table[Purchase] {

  sealed trait PaymentMethod extends EnumEntry with Uppercase
  object PaymentMethod extends Enum[PaymentMethod] {

    val values: IndexedSeq[PaymentMethod] = findValues
    final case object BLIK extends PaymentMethod
    final case object CASH extends PaymentMethod
    final case object CARD extends PaymentMethod
  }

  sealed trait ShippingMethod extends EnumEntry with Uppercase
  object ShippingMethod extends Enum[ShippingMethod] {

    val values: IndexedSeq[ShippingMethod] = findValues

    final case object PACZKOMAT extends ShippingMethod
    final case object DPD       extends ShippingMethod
    final case object POCZTA    extends ShippingMethod
  }

  sealed trait PurchaseStatus extends EnumEntry with Uppercase
  object PurchaseStatus extends Enum[PurchaseStatus] {

    val values: IndexedSeq[PurchaseStatus] = findValues

    final case object IN_PROGRESS extends PurchaseStatus
    final case object CANCELLED   extends PurchaseStatus
    final case object DELIVERED   extends PurchaseStatus
  }

  override val tableName: String = "purchase"
  override val generator: Gen[Purchase] = for {
    purchaseId     <- Generation.uuidGen
    paymentMethod  <- Generation.enumGen(PaymentMethod)
    date           <- Generation.timeFromGen(15)
    shippingMethod <- Generation.enumGen(ShippingMethod)
    purchaseStatus <- Generation.enumGen(PurchaseStatus)
  } yield
    Purchase(purchaseId,
             paymentMethod,
             date,
             shippingMethod,
             purchaseStatus,
             User.getRandomRow.userId,
             User.getRandomRow.userId,
             Address.getRandomRow.addressId)

  override def accessFields(purchase: Purchase): Iterator[String] = purchase.productElementNames
  override def accessValues(purchase: Purchase): Iterator[Any]    = purchase.productIterator
}
