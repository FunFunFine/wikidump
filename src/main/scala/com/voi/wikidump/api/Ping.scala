package com.voi.wikidump.api

import cats.Applicative
import cats.implicits._
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._

trait Ping[F[_]] {
  def ping(): F[Ping.Status]
}

object Ping {
  implicit def apply[F[_]](implicit ev: Ping[F]): Ping[F] = ev

  def impl[F[_] : Applicative]: Ping[F] = () => Status("Ok").pure[F]

  final case class Status(status:String) extends AnyVal

  object Status {
    implicit val greetingEncoder: Encoder[Status] = (a: Status) => Json.obj(
      ("Status", Json.fromString(a.status)),
    )

    implicit def greetingEntityEncoder[F[_] : Applicative]: EntityEncoder[F, Status] =
      jsonEncoderOf[F, Status]
  }
}
