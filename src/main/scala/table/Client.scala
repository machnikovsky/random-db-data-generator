package pl.machnikovsky.generator
package table

import generationUtil.{ DbInsert, Generation }
import table.Client.{ BirthDate, PESEL }

import faker.name.{ FirstName, LastName }
import fs2.io.file.Path
import org.scalacheck.Gen

import java.time.LocalDateTime
import java.util.UUID

final case class Client(clientId: UUID, firstName: FirstName, lastName: LastName, pesel: PESEL, birthDate: BirthDate)

object Client extends Table[Client] {

  final case class BirthDate private (value: LocalDateTime) extends AnyVal
  final case class PESEL private (value: String)        extends AnyVal

  override val tableName: String = "client"
  //override val rowsToGenerate: Long = 100_000L
  override val generator: Gen[Client] = for {
    clientId  <- Generation.uuidGen
    firstName <- Generation.firstNameGen
    lastName  <- Generation.lastNameGen
    pesel     <- Generation.peselGen
    birthDate <- Generation.birthDateGen
  } yield Client(clientId, firstName, lastName, pesel, birthDate)

  override implicit val dbInsert: DbInsert[Client] = (client: Client) =>
    s"insert into $tableName(client_id, first_name, last_name, pesel, birth_date) values ('${client.clientId}', '${client.firstName}', '${client.lastName}', '${client.pesel.value}', '${client.birthDate.value}');"
}
