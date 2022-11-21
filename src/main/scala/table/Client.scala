package pl.machnikovsky.generator
package table

import generationUtil.Generation

import faker.name.{ FirstName, LastName }
import org.scalacheck.Gen

import java.time.LocalDateTime
import java.util.UUID

final case class Client(clientId: UUID,
                        firstName: FirstName,
                        lastName: LastName,
                        pesel: String,
                        birthDate: LocalDateTime)

object Client extends Table[Client] {

  override val tableName: String = "client"
  //override val rowsTogenerate: Long = 100_000L
  override val generator: Gen[Client] = for {
    clientId  <- Generation.uuidGen
    firstName <- Generation.firstNameGen
    lastName  <- Generation.lastNameGen
    pesel     <- Generation.stringOfNNumsGen(11)
    birthDate <- Generation.timeFromToGen(120, 18)
  } yield Client(clientId, firstName, lastName, pesel, birthDate)

  override def accessFields(client: Client): Iterator[String] = client.productElementNames
  override def accessValues(client: Client): Iterator[Any]    = client.productIterator
}
