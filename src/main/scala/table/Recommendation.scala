package pl.machnikovsky.generator
package table

import generationUtil.{ DbInsert, Generation }

import fs2.io.file.Path
import org.scalacheck.Gen

import java.util.UUID
import scala.collection.mutable.ListBuffer

case class Recommendation(
    recommendationId: UUID,
    accountId: UUID,
    offerId: UUID,
    content: String,
    showedCount: Int,
    clickedCount: Int
)

object Recommendation extends Table[Recommendation] {

  override val tableName: String                        = "recommendation"
  override val filePath: Path                           = Path("src/main/resources/sql/data/tmp3/recommendation_tmp.sql")
  override val inMemoryList: ListBuffer[Recommendation] = ListBuffer()
  override val generator: Gen[Recommendation] = for {
    recommendationId <- Generation.uuidGen
    content          <- Generation.loremWordGen
    showedCount      <- Gen.choose(0, 100000)
    clickedCount     <- Gen.choose(0, showedCount)
  } yield
    Recommendation(recommendationId,
                   Account.getRandomRow.accountId,
                   Offer.getRandomRow.offerId,
                   content.value,
                   showedCount,
                   clickedCount)

  override implicit val dbInsert: DbInsert[Recommendation] = (recommendation: Recommendation) =>
    s"insert into $tableName(recommendation_id, account_id, offer_id, content, showed_count, clicked_count) values ('${recommendation.recommendationId}', '${recommendation.accountId}', '${recommendation.offerId}', '${recommendation.content}', '${recommendation.showedCount}', '${recommendation.clickedCount}');"

}
