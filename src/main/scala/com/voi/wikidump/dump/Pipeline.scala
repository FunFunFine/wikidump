package com.voi.wikidump.dump

object Pipeline {

  import cats.Monad
  import cats.syntax.functor._
  import cats.syntax.flatMap._

  def run[F[_] : Monad, S, T, R](source: F[S])(target: T => F[R])(map: S => T): F[R] = {
    for {
      data <- source
      items = map(data)
      res <- target(items)
    } yield res
  }
}