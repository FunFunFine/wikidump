package com.voi.wikidump.api

import java.util.UUID
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import cats.effect._
import com.voi.wikidump.MongoCreation
import org.mongodb.scala.MongoCollection
import org.mongodb.scala.bson.ObjectId

final case class MongoRepository[F[_] : Sync, A](pages: MongoCollection[A]) {
  val makeId: F[String] = Sync[F].delay {
    UUID.randomUUID().toString
  }

  def get(id: ObjectId): F[Option[A]] =
    Sync[F].delay {
      pages.find().filter
    }
}
import cats.implicits._

object MongoRepository {
  def empty[F[_] : Sync, A](name: String): F[MongoRepository[F, A]] = MongoCreation.getCollection[F, A]("wiki")
    .map(new MongoRepository[F, A](_))
}
