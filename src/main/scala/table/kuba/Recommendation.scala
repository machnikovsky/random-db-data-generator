package pl.machnikovsky.generator
package table.kuba

import generationUtil.Generation
import table.Table

import org.scalacheck.Gen

import java.util.UUID

case class Recommendation(
    recommendationId: UUID,
    accountId: UUID,
    offerId: UUID,
    content: String,
    showedCount: Int,
    clickedCount: Int
)

object Recommendation extends Table[Recommendation] {

  override val tableName: String = "recommendation"
  //override val rowsTogenerate: Long = 10_000L
  override val generator: Gen[Recommendation] = for {
    recommendationId <- Generation.uuidGen
    content          <- Generation.stringOfNCharsGen(30)
    showedCount      <- Generation.numFromTo(0, 100000)
    clickedCount     <- Generation.numFromTo(0, showedCount)
  } yield
    Recommendation(recommendationId,
                   Account.getRandomRow.accountId,
                   Offer.getRandomRow.offerId,
                   content,
                   showedCount,
                   clickedCount)

  override def accessFields(recommendation: Recommendation): Iterator[String] = recommendation.productElementNames
  override def accessValues(recommendation: Recommendation): Iterator[Any] = recommendation.productIterator
}
