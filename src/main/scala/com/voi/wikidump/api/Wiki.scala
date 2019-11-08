package com.voi.wikidump.api

import cats.effect.Sync
import com.voi.wikidump.data.PageData
import org.http4s.client.dsl.Http4sClientDsl

trait Wiki[F[_]]{
  def wiki(topic:String): F[PageData]
}


object Wiki {
  def apply[F[_]](implicit ev: Wiki[F]): Wiki[F] = ev

  final case class JokeError(e: Throwable) extends RuntimeException

  def impl[F[_]: Sync]: Wiki[F] = new Wiki[F]{
    val dsl: Http4sClientDsl[F] = new Http4sClientDsl[F]{}
    def wiki(topic:String): F[PageData] = ???
  }
}