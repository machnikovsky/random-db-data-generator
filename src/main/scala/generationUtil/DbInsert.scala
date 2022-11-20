package pl.machnikovsky.generator
package generationUtil

trait DbInsert[A] {
  def toDbInsert(x: A): String
}

object DbInsert {
  def apply[A](implicit instance: DbInsert[A]): DbInsert[A] = instance
}

object DbInsertSyntax {
  implicit class DbInsertOps[A](x: A) {
    def toDbInsert(implicit dbInsert: DbInsert[A]): String = dbInsert.toDbInsert(x)
  }
}
