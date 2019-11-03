package com.voi.wikidump

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    WikidumpServer.stream[IO].compile.drain.as(ExitCode.Success)
}