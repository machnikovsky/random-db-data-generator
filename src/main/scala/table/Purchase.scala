package pl.machnikovsky.generator
package table

import generationUtil.{ DbInsert, Generation }
import table.Purchase.{ PaymentMethod, PurchaseDate }

import fs2.io.file.Path
import org.scalacheck.Gen

import java.time.LocalDate
import java.util.UUID
import scala.collection.mutable.ListBuffer

case class Purchase(
    purchaseId: UUID,
    accountId: UUID,
    offerId: UUID,
    quantity: Int,
    paymentMethod: PaymentMethod,
    date: PurchaseDate
)

object Purchase extends Table[Purchase] {

  final case class PurchaseDate(value: LocalDate) extends AnyVal

  sealed trait PaymentMethod
  object PaymentMethod {
    final case object CREDIT extends PaymentMethod
    final case object CARD   extends PaymentMethod
  }

  override val tableName: String                  = "purchase"
  override val filePath: Path                     = Path("src/main/resources/sql/data/tmp3/purchase_tmp.sql")
  override val inMemoryList: ListBuffer[Purchase] = ListBuffer()
  override val generator: Gen[Purchase] = for {
    purchaseId    <- Generation.uuidGen
    quantity      <- Gen.choose(0, 50)
    paymentMethod <- Generation.paymentMethodGen
    date          <- Generation.purchaseDateGen
  } yield
    Purchase(purchaseId, Account.getRandomRow.accountId, Offer.getRandomRow.offerId, quantity, paymentMethod, date)

  override implicit val dbInsert: DbInsert[Purchase] = (purchase: Purchase) =>
    s"insert into $tableName(purchase_id, account_id, offer_id, quantity, payment_method, date) values ('${purchase.purchaseId}', '${purchase.accountId}', '${purchase.offerId}', '${purchase.quantity}', '${purchase.paymentMethod}', '${purchase.date.value}');"

}
