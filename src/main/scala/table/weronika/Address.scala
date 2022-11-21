package pl.machnikovsky.generator
package table.weronika

import generationUtil.Generation
import table.Table

import org.scalacheck.Gen

import java.util.UUID

case class Address(
    addressId: UUID,
    city: String,
    number: String,
    street: String,
    postalCode: String
)

object Address extends Table[Address] {

  override val tableName: String = "address"
  override val generator: Gen[Address] = for {
    addressId  <- Generation.uuidGen
    city       <- Generation.stringOfNCharsGen(10)
    number     <- Generation.stringOfNNumsGen(3)
    street     <- Generation.stringOfNCharsGen(10)
    postalCode <- Generation.stringOfNNumsGen(5)
  } yield Address(addressId, city, number, street, postalCode)

  override def accessFields(address: Address): Iterator[String] = address.productElementNames
  override def accessValues(address: Address): Iterator[Any]    = address.productIterator

}
