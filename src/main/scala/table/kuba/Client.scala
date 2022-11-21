package pl.machnikovsky.generator
package table.kuba

import generationUtil.Generation
import table.Table
import table.kuba.Client.Gender

import enumeratum.EnumEntry.Uppercase
import enumeratum._
import faker.name.{ FirstName, LastName }
import org.scalacheck.Gen

import java.time.LocalDateTime
import java.util.UUID

final case class Client(clientId: UUID,
                        addressId: UUID,
                        firstName: FirstName,
                        lastName: LastName,
                        gender: Gender,
                        pesel: String,
                        birthDate: LocalDateTime)

object Client extends Table[Client] {

  sealed trait Gender extends EnumEntry with Uppercase
  object Gender extends Enum[Gender] {
    val values: IndexedSeq[Gender] = findValues

    final case object MALE    extends Gender
    final case object FEMALE  extends Gender
    final case object UNKNOWN extends Gender
  }

  override val tableName: String = "client"
  //override val rowsTogenerate: Long = 100_000L
  override val generator: Gen[Client] = for {
    clientId  <- Generation.uuidGen
    firstName <- Generation.firstNameGen
    lastName  <- Generation.lastNameGen
    gender    <- Generation.enumGen(Gender)
    pesel     <- Generation.stringOfNNumsGen(11)
    birthDate <- Generation.timeFromToGen(120, 18)
  } yield Client(clientId, Address.getRandomRow.addressId, firstName, lastName, gender, pesel, birthDate)

  override def accessFields(client: Client): Iterator[String] = client.productElementNames
  override def accessValues(client: Client): Iterator[Any]    = client.productIterator
}
