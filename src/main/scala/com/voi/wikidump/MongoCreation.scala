package com.voi.wikidump

import cats.effect.Sync
import org.mongodb.scala._

object MongoCreation {
  val dbName: String = "wikidump_db"

  def getCollection[F[_] : Sync, A](name: String): F[MongoCollection[A]] = Sync[F].delay {
    import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
    import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
    import org.mongodb.scala.bson.codecs.Macros._

    val codecRegistry = fromRegistries(fromProviders(classOf[A]), DEFAULT_CODEC_REGISTRY)
    MongoClient().getDatabase(dbName).withCodecRegistry(codecRegistry).getCollection(name)
  }
}
