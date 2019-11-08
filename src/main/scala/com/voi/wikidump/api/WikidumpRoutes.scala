package com.voi.wikidump.api

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object WikidumpRoutes {
  def pingRoutes[F[_] : Sync](P: Ping[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "ping" =>
        for {
          status <- P.ping()
          resp <- Ok(status)
        } yield resp
    }
  }

  def wikiRoutes[F[_] : Sync](W: Wiki[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "wiki" / title =>
        for {
          pageData <- W.wiki(title)
          resp <- Ok(pageData)
        } yield resp
    }
  }
}