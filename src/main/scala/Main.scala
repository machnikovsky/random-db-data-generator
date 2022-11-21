package pl.machnikovsky.generator

import generationUtil.StreamUtils
import table._

import cats.effect.{ ExitCode, IO, IOApp }
import cats.instances.list._
import cats.syntax.parallel._

object Main extends IOApp {

  // To change generated tables, you only need to modify this list
  val tables: List[Table[_]] = List(
    Client,
    Item,
    Account,
    Offer,
    ShoppingCart,
    Recommendation,
    Purchase,
    Return,
    CartOffer
  )

  override def run(args: List[String]): IO[ExitCode] =
    tables
      .parTraverse_[IO, Unit](StreamUtils.runAndCountTime)
      .as(ExitCode.Success)

}
