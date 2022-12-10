package pl.machnikovsky.generator
package generationUtil

import enumeratum.{Enum, EnumEntry}
import faker.ResourceLoader.Implicits.defaultResourceLoader
import faker.name.{FirstName, LastName}
import org.scalacheck.{Arbitrary, Gen}

import java.time.LocalDateTime
import java.util.UUID

object Generation {

  val uuidGen: Gen[UUID]   = Gen.uuid
  val firstNameGen: Gen[FirstName]                 = Arbitrary.arbitrary[FirstName]
  val lastNameGen: Gen[LastName]                   = Arbitrary.arbitrary[LastName]
  val priceGen: Gen[BigDecimal] = Gen.choose(BigDecimal.decimal(1).setScale(2), BigDecimal.decimal(1000).setScale(2))

  def timeFromToGen(from: Int, to: Int): Gen[LocalDateTime] = Gen.choose(LocalDateTime.now().minusYears(from), LocalDateTime.now().minusYears(to))
  def timeFromGen(from: Int): Gen[LocalDateTime] = Gen.choose(LocalDateTime.now().minusYears(from), LocalDateTime.now())
  def enumGen[T <: EnumEntry](enum: Enum[T]): Gen[T] = Gen.oneOf(enum.values)
  def stringOfNCharsGen(n: Int): Gen[String] = Gen.stringOfN(n, Gen.alphaChar)
  def stringOfNNumsGen(n: Int): Gen[String] = Gen.stringOfN(n, Gen.numChar)
  def numFromTo(from: Int, to: Int): Gen[Int] = Gen.choose(from, to)

}
