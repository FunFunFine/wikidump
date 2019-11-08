package com.voi.wikidump.data

import java.time.ZonedDateTime

import cats.Applicative
import cats.effect.Sync
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.{EntityDecoder, EntityEncoder}
import org.mongodb.scala.bson.ObjectId
object PageData {

  implicit val dataEncoder: Encoder[PageData] = deriveEncoder
  implicit val dataDecoder: Decoder[PageData] = deriveDecoder
  type DateTime = ZonedDateTime
  implicit def pageEntityDecoder[F[_]: Sync]: EntityDecoder[F, PageData] =
    jsonOf
  implicit def pageEntityEncoder[F[_]: Applicative]: EntityEncoder[F, PageData] =
    jsonEncoderOf

}
import com.voi.wikidump.data.PageData.DateTime

case class PageData(create_timestamp: DateTime,
                    timestamp: DateTime,
                    language: String,
                    wiki: String,
                    category: List[String],
                    title: String,
                    auxiliary_text: String
                   )

case class PageDataWithId(_id: ObjectId,
                          create_timestamp: ZonedDateTime,
                          timestamp: DateTime,
                          language: String,
                          wiki: String,
                          category: List[String],
                          title: String,
                          auxiliary_text: String
                         )

object PageDataWithId {
  def apply(pageData: PageData): PageDataWithId
  = pageData match {
    case PageData(create_timestamp: DateTime, timestamp: DateTime, language: String, wiki: String, category: List[String], title: String, auxiliary_text: String) => new PageDataWithId(new ObjectId(), create_timestamp, timestamp, language, wiki, category, title, auxiliary_text)
  }
}

