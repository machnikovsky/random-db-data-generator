package pl.machnikovsky.generator
package table.weronika

import generationUtil.Generation
import table.Table
import table.weronika.User.{Gender, Role}

import enumeratum.EnumEntry.Uppercase
import enumeratum._
import org.scalacheck.Gen

import java.time.LocalDateTime
import java.util.UUID

final case class User(userId: UUID,
                      username: String,
                      email: String,
                      password: String,
                      firstName: String,
                      lastName: String,
                      birthDate: LocalDateTime,
                      createdAt: LocalDateTime,
                      gender: Gender,
                      role: Role,
                      addressId: UUID)

object User extends Table[User] {

  sealed trait Gender extends EnumEntry with Uppercase

  object Gender extends Enum[Gender] {
    val values: IndexedSeq[Gender] = findValues

    final case object MALE extends Gender

    final case object FEMALE extends Gender

    final case object UNKNOWN extends Gender
  }

  sealed trait Role extends EnumEntry with Uppercase

  object Role extends Enum[Role] {
    val values: IndexedSeq[Role] = findValues

    final case object USER extends Role

    final case object ADMIN extends Role
  }

  override val tableName: String = "user"
  override val generator: Gen[User] =
    for {
      userId <- Generation.uuidGen
      username <- Generation.stringOfNCharsGen(10)
      email <- Generation.stringOfNCharsGen(10)
      password <- Generation.stringOfNCharsGen(10)
      firstName <- Generation.firstNameGen
      lastName <- Generation.lastNameGen
      birthDate <- Generation.timeFromToGen(120, 18)
      createdAt <- Generation.timeFromGen(15)
      gender <- Generation.enumGen(Gender)
      role <- Generation.enumGen(Role)
    } yield
      User(userId,
        username,
        email,
        password,
        firstName.value,
        lastName.value,
        birthDate,
        createdAt,
        gender,
        role,
        Address.getRandomRow.addressId)

  override def accessFields(user: User): Iterator[String] = user.productElementNames

  override def accessValues(user: User): Iterator[Any] = user.productIterator

}
