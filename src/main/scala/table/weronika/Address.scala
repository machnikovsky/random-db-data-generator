package pl.machnikovsky.generator
package table.weronika

import generationUtil.Generation
import table.Table

import enumeratum.{Enum, EnumEntry}
import enumeratum.EnumEntry.Uppercase
import org.scalacheck.Gen
import pl.machnikovsky.generator.table.weronika.Address.Voivodeship

import java.util.UUID

case class Address(
                    addressId: UUID,
                    city: String,
                    number: String,
                    street: String,
                    postalCode: String,
                    voivodeship: Voivodeship
                  )

object Address extends Table[Address] {

  sealed trait Voivodeship extends EnumEntry with Uppercase

  object Voivodeship extends Enum[Voivodeship] {
    val values: IndexedSeq[Voivodeship] = findValues

    final case object DOLNOSLASKIE extends Voivodeship

    final case object KUJAWSKOPOMORSKIE extends Voivodeship

    final case object LUBELSKIE extends Voivodeship

    final case object LUBUSKIE extends Voivodeship

    final case object LODZKIE extends Voivodeship

    final case object MALOPOLSKIE extends Voivodeship

    final case object MAZOWIECKIE extends Voivodeship

    final case object OPOLSKIE extends Voivodeship

    final case object PODKARPACKIE extends Voivodeship

    final case object PODLASKIE extends Voivodeship

    final case object POMORSKIE extends Voivodeship

    final case object SLASKIE extends Voivodeship

    final case object SWIETOKRZYSKIE extends Voivodeship

    final case object WARMINSKOMAZURSKIE extends Voivodeship

    final case object WIELKOPOLSKIE extends Voivodeship

    final case object ZACHODNIOPOMORSKIE extends Voivodeship
  }

  override val tableName: String = "address"
  override val generator: Gen[Address] = for {
    addressId <- Generation.uuidGen
    city <- Generation.stringOfNCharsGen(10)
    number <- Generation.stringOfNNumsGen(3)
    street <- Generation.stringOfNCharsGen(10)
    postalCode <- Generation.stringOfNNumsGen(5)
    voiwodeship <- Generation.enumGen(Voivodeship)
  } yield Address(addressId, city, number, street, postalCode, voiwodeship)

  override def accessFields(address: Address): Iterator[String] = address.productElementNames

  override def accessValues(address: Address): Iterator[Any] = address.productIterator

}
