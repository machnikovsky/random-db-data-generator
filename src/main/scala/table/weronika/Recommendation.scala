package pl.machnikovsky.generator
package table.weronika

import generationUtil.Generation
import table.Table

import org.scalacheck.Gen

import java.util.UUID

case class Recommendation(
    recommendationId: UUID,
    itemId: UUID,
    text: String,
    showedCount: Int,
    clickedCount: Int
)

object Recommendation extends Table[Recommendation] {

  override val tableName: String = "recommendation"
  override val generator: Gen[Recommendation] = for {
    recommendationId <- Generation.uuidGen
    text             <- Generation.stringOfNCharsGen(20)
    showedCount      <- Generation.numFromTo(0, 100000)
    clickedCount     <- Generation.numFromTo(0, showedCount)
  } yield Recommendation(recommendationId, Item.getRandomRow.itemId, text, showedCount, clickedCount)

  override def accessFields(recommendation: Recommendation): Iterator[String] = recommendation.productElementNames
  override def accessValues(recommendation: Recommendation): Iterator[Any]    = recommendation.productIterator
}
