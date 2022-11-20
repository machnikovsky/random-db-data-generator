package pl.machnikovsky.generator
package table

import generationUtil.{ DbInsert, Generation }
import table.Account.{ BankAccountNumber, CreatedAt }

import fs2.io.file.Path
import org.scalacheck.Gen

import java.time.LocalDateTime
import java.util.UUID

final case class Account(accountId: UUID,
                         clientId: UUID,
                         login: String,
                         password: String,
                         email: String,
                         phoneNumber: String,
                         bankAccountNumber: BankAccountNumber,
                         createdAt: CreatedAt)

object Account extends Table[Account] {

  final case class BankAccountNumber private (value: String) extends AnyVal
  final case class CreatedAt private (value: LocalDateTime)      extends AnyVal

  override val tableName: String = "account"
  //override val rowsToGenerate: Long = 100_000L
  override val generator: Gen[Account] =
    for {
      accountId         <- Generation.uuidGen
      login             <- Gen.stringOfN(10, Gen.alphaChar)
      password          <- Gen.stringOfN(10, Gen.alphaChar)
      email             <- Gen.stringOfN(10, Gen.alphaChar)
      phoneNumber       <- Gen.stringOfN(9, Gen.numChar)
      bankAccountNumber <- Generation.bankAccountNumberGen
      createdAt         <- Generation.createdAtGen
    } yield
      Account(accountId,
              Client.getRandomRow.clientId,
              login,
              password,
              email,
              phoneNumber,
              bankAccountNumber,
              createdAt)

  override implicit val dbInsert: DbInsert[Account] = (client: Account) =>
    s"insert into $tableName(account_id, client_id, login, password, email, phone_number, bank_account_number, created_at) values ('${client.accountId}', '${client.clientId}', '${client.login}', '${client.password}', '${client.email}', '${client.phoneNumber}', '${client.bankAccountNumber.value}', '${client.createdAt.value}');"

}
