package com.voi.wikidump.dump

import java.io.File

import cats.effect.{ExitCode, IO, IOApp, Resource}
import com.voi.wikidump.MongoCreation
import com.voi.wikidump.data.PageData
import io.circe.parser.decode

import scala.io.{BufferedSource, Source}

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = for {
    _ <- if (args.length < 1) IO.raiseError(new IllegalArgumentException("Specify data file"))
    else IO.unit
    sourceFile = new File(args.head)
    _ <- Pipeline.run(fileLinesSource(sourceFile))(mongoTarget)(fromJson)
  } yield ExitCode.Success

  def mongoTarget(items: Iterator[PageData]): IO[Unit] = {
    for {
      collection <- MongoCreation.getCollection[IO, PageData]("wikidump")
      _ <- IO(collection.insertMany(items.toSeq))
    } yield ()
  }

  def fromJson(jsons: Iterator[String]): Iterator[PageData] =
    jsons.map(decode[PageData]).collect {
      case Right(wp) => wp
    }

  def fileLinesSource(f: File): IO[Iterator[String]] = input(f).use(s => IO(s.getLines()))

  def input(f: File): Resource[IO, BufferedSource] =
    Resource.make {
      IO(Source.fromFile(f))
    } { input =>
      IO(input.close()).handleErrorWith(_ => IO.unit) // release
    }
}



