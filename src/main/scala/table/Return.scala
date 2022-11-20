package pl.machnikovsky.generator
package table

import generationUtil.{ DbInsert, Generation }
import table.Return.ReturnDate

import fs2.io.file.Path
import org.scalacheck.Gen

import java.time.LocalDateTime
import java.util.UUID

case class Return(
    returnId: UUID,
    accountId: UUID,
    offerId: UUID,
    quantity: Int,
    date: ReturnDate
)

object Return extends Table[Return] {

  final case class ReturnDate(value: LocalDateTime) extends AnyVal

  override val tableName: String = "return"
  //override val rowsToGenerate: Long = 20_000L
  override val generator: Gen[Return] = for {
    returnId <- Generation.uuidGen
    quantity <- Gen.choose(0, 50)
    date     <- Generation.returnDateGen
  } yield Return(returnId, Account.getRandomRow.accountId, Offer.getRandomRow.offerId, quantity, date)

  override implicit val dbInsert: DbInsert[Return] = (`return`: Return) =>
    s"insert into $tableName(return_id, account_id, offer_id, quantity, date) values ('${`return`.returnId}', '${`return`.accountId}', '${`return`.offerId}', '${`return`.quantity}','${`return`.date.value}');"

}
