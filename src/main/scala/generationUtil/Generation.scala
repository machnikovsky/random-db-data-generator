package pl.machnikovsky.generator
package generationUtil

import table.Account.{BankAccountNumber, CreatedAt}
import table.Client.{BirthDate, PESEL}
import table.Item.Category
import table.Offer.{OfferType, PublicationDate, ShipmentType}
import table.Purchase.{PaymentMethod, PurchaseDate}
import table.Return.ReturnDate
import table.ShoppingCart.Stage

import faker.ResourceLoader.Implicits.defaultResourceLoader
import faker.internet.{EmailAddress, Password}
import faker.lorem.{LoremSentence, LoremWord}
import faker.name.{FirstName, LastName, UserName}
import faker.phone.PhoneNumber
import org.scalacheck.{Arbitrary, Gen}

import java.time.LocalDate
import java.util.UUID

object Generation {

  val uuidGen: Gen[UUID]   = Gen.uuid
  val peselGen: Gen[PESEL] = Gen.stringOfN(11, Gen.numChar).map(PESEL)
  val birthDateGen: Gen[BirthDate] =
    Gen.choose(LocalDate.now().minusYears(120), LocalDate.now().minusYears(18)).map(BirthDate)
  val firstNameGen: Gen[FirstName]                 = Arbitrary.arbitrary[FirstName]
  val lastNameGen: Gen[LastName]                   = Arbitrary.arbitrary[LastName]
  val loginGen: Gen[UserName]                      = Arbitrary.arbitrary[UserName]
  val passwordGen: Gen[Password]                   = Arbitrary.arbitrary[Password]
  val emailGen: Gen[EmailAddress]                  = Arbitrary.arbitrary[EmailAddress]
  val phoneNumberGen: Gen[PhoneNumber]             = Arbitrary.arbitrary[PhoneNumber]
  val bankAccountNumberGen: Gen[BankAccountNumber] = Gen.stringOfN(22, Gen.numChar).map(BankAccountNumber)
  val createdAtGen: Gen[CreatedAt] =
    Gen.choose(LocalDate.now().minusYears(15), LocalDate.now()).map(CreatedAt)
  val publicationDateGen: Gen[PublicationDate] =
    Gen.choose(LocalDate.now().minusYears(15), LocalDate.now()).map(PublicationDate)
  val purchaseDateGen: Gen[PurchaseDate] =
    Gen.choose(LocalDate.now().minusYears(15), LocalDate.now()).map(PurchaseDate)
  val returnDateGen: Gen[ReturnDate] =
    Gen.choose(LocalDate.now().minusYears(15), LocalDate.now()).map(ReturnDate)
  val loremWordGen: Gen[LoremWord]         = Arbitrary.arbitrary[LoremWord]
  val loremSentenceGen: Gen[LoremSentence] = Arbitrary.arbitrary[LoremSentence]
  val categoryGen: Gen[Category] = Gen.oneOf(Category.INNE,
                                                  Category.OBUWIE,
                                                  Category.UBRANIA,
                                                  Category.ZABAWKI,
                                                  Category.SAMOCHODY,
                                                  Category.NIERUCHOMOSCI)
  val priceGen: Gen[BigDecimal] = Gen.choose(BigDecimal.decimal(1.00), BigDecimal.decimal(1_000_000_000))
  val shipmentTypeGen: Gen[ShipmentType] = Gen.oneOf(
    ShipmentType.KURIER,
    ShipmentType.POCZTA,
    ShipmentType.PACZKOMAT,
    ShipmentType.ODBIOR_OSOBISTY
  )

  val offerTypeGen: Gen[OfferType] = Gen.oneOf(
    OfferType.OGLOSZENIE,
    OfferType.LICYTACJA,
    OfferType.KUP_TERAZ
  )
  val stageGen: Gen[Stage] = Gen.oneOf(Stage.PUSTY, Stage.PLATNOSC, Stage.ZAPELNIONY)
  val paymentMethodGen: Gen[PaymentMethod] = Gen.oneOf(PaymentMethod.CARD, PaymentMethod.CREDIT)
}
