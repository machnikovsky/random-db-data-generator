package pl.machnikovsky.generator
package table

import generationUtil.{DbInsert, Generation}

import org.scalacheck.Gen

import java.time.LocalDateTime
import java.util.UUID

final case class Account(accountId: UUID,
                         clientId: UUID,
                         login: String,
                         password: String,
                         email: String,
                         phoneNumber: String,
                         bankAccountNumber: String,
                         createdAt: LocalDateTime)

object Account extends Table[Account] {

  override val tableName: String = "account"
  //override val rowsTogenerate: Long = 100_000L
  override val generator: Gen[Account] =
    for {
      accountId         <- Generation.uuidGen
      login             <- Generation.stringOfNCharsGen(10)
      password          <- Generation.stringOfNCharsGen(10)
      email             <- Generation.stringOfNCharsGen(10)
      phoneNumber       <- Generation.stringOfNNumsGen(9)
      bankAccountNumber <- Generation.stringOfNNumsGen(22)
      createdAt         <- Generation.timeFromGen(15)
    } yield
      Account(accountId,
              Client.getRandomRow.clientId,
              login,
              password,
              email,
              phoneNumber,
              bankAccountNumber,
              createdAt)

  override def accessFields(account: Account): Iterator[String] = account.productElementNames
  override def accessValues(account: Account): Iterator[Any] = account.productIterator

}
