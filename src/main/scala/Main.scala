package pl.machnikovsky.generator

import generationUtil.StreamUtils
import table._

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {

  // TODO: change date type to include time!!!!

  // Define number of rows per table
  val rows: Long = 5_000L

  // List of tables you want to fill, ORDER MATTERS
//  val tables: List[Table[_]] =
//    List(Client, Account, Item, Offer, Recommendation, ShoppingCart, CartOffer, Purchase, Return)


  //tables.foreach { table =>
  //      StreamUtils.runAndCountTime(table.generateData, rows, table.tableName).unsafeRunSync()
  //    }

  private val generateDatabaseData: IO[Unit] = for {
    client <- StreamUtils.runAndCountTime(Client.generateData, rows, Client.tableName).start
    item <- StreamUtils.runAndCountTime(Item.generateData, rows, Item.tableName).start
    _ <- client.join
    _ <- item.join

    account <- StreamUtils.runAndCountTime(Account.generateData, rows, Account.tableName).start
    offer <- StreamUtils.runAndCountTime(Offer.generateData, rows, Offer.tableName).start
    _ <- offer.join
    _ <- account.join

    shoppingCart <- StreamUtils.runAndCountTime(ShoppingCart.generateData, rows, ShoppingCart.tableName).start
    recommendation <- StreamUtils.runAndCountTime(Recommendation.generateData, rows, Recommendation.tableName).start
    purchase <- StreamUtils.runAndCountTime(Purchase.generateData, rows, Purchase.tableName).start
    ret <- StreamUtils.runAndCountTime(Return.generateData, rows, Return.tableName).start

    _ <- shoppingCart.join

    cartOffer <- StreamUtils.runAndCountTime(CartOffer.generateData, rows, CartOffer.tableName).start

    _ <- recommendation.join
    _ <- purchase.join
    _ <- ret.join
    _ <- cartOffer.join
  } yield ()

  override def run(args: List[String]): IO[ExitCode] = generateDatabaseData.as(ExitCode.Success)








}
