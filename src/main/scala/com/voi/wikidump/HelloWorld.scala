package com.voi.wikidump

import cats.Applicative
import cats.implicits._
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._

trait HelloWorld[F[_]] {
  def hello(name: HelloWorld.Name): F[HelloWorld.Greeting]
}

object HelloWorld {
  implicit def apply[F[_]](implicit ev: HelloWorld[F]): HelloWorld[F] = ev

  final case class Name(name: String) extends AnyVal

  /**
    * More generally you will want to decouple your edge representations from
    * your internal data structures, however this shows how you can
    * create encoders for your data.
    **/
  final case class Greeting(greeting: String) extends AnyVal

  object Greeting {
    implicit val greetingEncoder: Encoder[Greeting] = (a: Greeting) => Json.obj(
      ("message", Json.fromString(a.greeting)),
    )

    implicit def greetingEntityEncoder[F[_] : Applicative]: EntityEncoder[F, Greeting] =
      jsonEncoderOf[F, Greeting]
  }

  def impl[F[_] : Applicative]: HelloWorld[F] = (n: HelloWorld.Name) => Greeting("Hello, " + n.name).pure[F]
}

case class Hut(name: String)

case class HutWithId(id: String, hut: Hut)

import java.util.UUID

import cats.effect._
import cats.implicits._

import scala.collection.mutable.ListBuffer

final case class HutRepository[F[_]: Sync](private val huts: ListBuffer[HutWithId]){
  val makeId: F[String] = Sync[F].delay {
    UUID.randomUUID().toString
  }

  def getHut(id: String): F[Option[HutWithId]] =
    Sync[F].delay {
      huts.find(_.id == id)
    }

  def addHut(hut: Hut): F[String] =
    for {
      uuid <- makeId
      _ <- Sync[F].delay {
        huts += HutWithId(uuid, hut)
      }
    } yield uuid

  def updateHut(hutWithId: HutWithId): F[Unit] = {
    for {
      _ <- Sync[F].delay {
        huts -= hutWithId
      }
      _ <- Sync[F].delay {
        huts += hutWithId
      }
    } yield ()
  }

  def deleteHut(hutId: String): F[Unit] =
    Sync[F].delay {
      huts.find(_.id == hutId).foreach(h => huts -= h)
    }
}

object HutRepository {
  def empty[F[_]](implicit f: Sync[F]): F[HutRepository[F]] = f.delay {
    new HutRepository[F](ListBuffer())
  }
}