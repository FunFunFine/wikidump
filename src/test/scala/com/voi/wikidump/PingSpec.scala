package com.voi.wikidump

import cats.effect.IO
import com.voi.wikidump.api.{Ping, WikidumpRoutes}
import org.http4s._
import org.http4s.implicits._
import org.specs2.matcher.MatchResult
import com.voi.wikidump.data._


class PingSpec extends org.specs2.mutable.Specification {

  "HelloWorld" >> {
    "return 200" >> {
      uriReturns200()
    }
    "return hello world" >> {
      uriReturnsHelloWorld()
    }
    "parse index" >> {
      DescriptionIsParsedCorrectly()
    }
  }
  import com.voi.wikidump.data.PageData._
  import io.circe.parser.decode
  private[this] val retHelloWorld: Response[IO] = {
    val getHW = Request[IO](Method.GET, uri"/hello/world")
    val helloWorld = Ping.impl[IO]
    WikidumpRoutes.pingRoutes(helloWorld).orNotFound(getHW).unsafeRunSync()
  }

  private[this] def uriReturns200(): MatchResult[Status] =
    retHelloWorld.status must beEqualTo(Status.Ok)

  private[this] def uriReturnsHelloWorld(): MatchResult[String] =
    retHelloWorld.as[String].unsafeRunSync() must beEqualTo("{\"message\":\"Hello, world\"}")

  private[this] def DescriptionIsParsedCorrectly(): MatchResult[Either[io.circe.Error, PageData]] = {
    val json =
      "{\n    \"template\": [\n        \"\\u0428\\u0430\\u0431\\u043b\\u043e\\u043d:\\u0417\\u0430\\u043a\\u0440\\u044b\\u0442\\u043e\",\n        \"\\u0428\\u0430\\u0431\\u043b\\u043e\\u043d:\\u0417\\u0430\\u043a\\u0440\\u044b\\u0442\\u043e\\/styles.css\",\n        \"\\u0428\\u0430\\u0431\\u043b\\u043e\\u043d:\\u0417\\u0430\\u043a\\u0440\\u044b\\u0442\\u043e\\/\"\n    ],\n    \"redirect\": [],\n    \"heading\": [\n        \"\\u0422\\u043e\\u0441\\u043a\\u0430\"\n    ],\n    \"source_text\": \"== [[\\u0422\\u043e\\u0441\\u043a\\u0430]] ==\\n{{\\u0437\\u0430\\u043a\\u0440\\u044b\\u0442\\u043e}}\\n[[\\u0422\\u043e\\u0441\\u043a\\u0430|\\u0411\\u0435\\u0441\\u0441\\u043c\\u044b\\u0441\\u043b\\u0435\\u043d\\u043d\\u043e\\u0435 \\u0441\\u043e\\u0434\\u0435\\u0440\\u0436\\u0430\\u043d\\u0438\\u0435]]. \\u0423\\u0434\\u0430\\u043b\\u0438\\u0442\\u044c. --[[\\u0423\\u0447\\u0430\\u0441\\u0442\\u043d\\u0438\\u043a:Ingvar-fed|Ingvar-fed]] ([[\\u041e\\u0431\\u0441\\u0443\\u0436\\u0434\\u0435\\u043d\\u0438\\u0435 \\u0443\\u0447\\u0430\\u0441\\u0442\\u043d\\u0438\\u043a\\u0430:Ingvar-fed|\\u043e\\u0431\\u0441\\u0443\\u0436\\u0434\\u0435\\u043d\\u0438\\u0435]]) 20:45, 29 \\u043c\\u0430\\u044f 2012 (UTC)\\n\\n\\u0411\\u0435\\u0441\\u0441\\u043c\\u044b\\u0441\\u043b\\u0435\\u043d\\u043d\\u043e\\u0435 \\u0441\\u043e\\u0434\\u0435\\u0440\\u0436\\u0430\\u043d\\u0438\\u0435. \\u0423\\u0434\\u0430\\u043b\\u0438\\u0442\\u044c. \\u0412\\u043e\\u0437\\u043c\\u043e\\u0436\\u043d\\u043e, \\u043d\\u0430\\u0439\\u0434\\u0443\\u0442\\u0441\\u044f \\u0436\\u0435\\u043b\\u0430\\u044e\\u0449\\u0438\\u0435 \\u043f\\u0435\\u0440\\u0435\\u043f\\u0438\\u0441\\u0430\\u0442\\u044c. --[[\\u0423\\u0447\\u0430\\u0441\\u0442\\u043d\\u0438\\u043a:Ingvar-fed|Ingvar-fed]] ([[\\u041e\\u0431\\u0441\\u0443\\u0436\\u0434\\u0435\\u043d\\u0438\\u0435 \\u0443\\u0447\\u0430\\u0441\\u0442\\u043d\\u0438\\u043a\\u0430:Ingvar-fed|\\u043e\\u0431\\u0441\\u0443\\u0436\\u0434\\u0435\\u043d\\u0438\\u0435]]) 20:47, 29 \\u043c\\u0430\\u044f 2012 (UTC)\\n\\n\\u041f\\u0435\\u0440\\u0435\\u043f\\u0438\\u0441\\u0430\\u043d\\u0430. --[[\\u0423\\u0447\\u0430\\u0441\\u0442\\u043d\\u0438\\u043a:\\u0411\\u0440\\u0430\\u0442\\u044c\\u044f \\u0421\\u0442\\u043e\\u044f\\u043b\\u043e\\u0432\\u044b (\\u0414\\u041c)|\\u0411\\u0440\\u0430\\u0442\\u044c\\u044f \\u0421\\u0442\\u043e\\u044f\\u043b\\u043e\\u0432\\u044b (\\u0414\\u041c)]] ([[\\u041e\\u0431\\u0441\\u0443\\u0436\\u0434\\u0435\\u043d\\u0438\\u0435 \\u0443\\u0447\\u0430\\u0441\\u0442\\u043d\\u0438\\u043a\\u0430:\\u0411\\u0440\\u0430\\u0442\\u044c\\u044f \\u0421\\u0442\\u043e\\u044f\\u043b\\u043e\\u0432\\u044b (\\u0414\\u041c)|\\u043e\\u0431\\u0441\\u0443\\u0436\\u0434\\u0435\\u043d\\u0438\\u0435]]) 00:08, 12 \\u043d\\u043e\\u044f\\u0431\\u0440\\u044f 2015 (UTC)\\n{{\\u0437\\u0430\\u043a\\u0440\\u044b\\u0442\\u043e\\/}}\",\n    \"version_type\": \"external\",\n    \"wiki\": \"ruwikiquote\",\n    \"auxiliary_text\": [],\n    \"language\": \"ru\",\n    \"title\": \"\\u041a \\u0443\\u0434\\u0430\\u043b\\u0435\\u043d\\u0438\\u044e\\/\\u0422\\u043e\\u0441\\u043a\\u0430\",\n    \"version\": 272802,\n    \"external_link\": [],\n    \"namespace_text\": \"\\u0412\\u0438\\u043a\\u0438\\u0446\\u0438\\u0442\\u0430\\u0442\\u043d\\u0438\\u043a\",\n    \"namespace\": 4,\n    \"text_bytes\": 839,\n    \"incoming_links\": 1,\n    \"text\": \"\\u0417\\u0434\\u0435\\u0441\\u044c \\u043d\\u0430\\u0445\\u043e\\u0434\\u044f\\u0442\\u0441\\u044f \\u0437\\u0430\\u0432\\u0435\\u0440\\u0448\\u0438\\u0432\\u0448\\u0438\\u0435\\u0441\\u044f \\u043e\\u0431\\u0441\\u0443\\u0436\\u0434\\u0435\\u043d\\u0438\\u044f. \\u041f\\u0440\\u043e\\u0441\\u044c\\u0431\\u0430 \\u043d\\u0435 \\u0432\\u043d\\u043e\\u0441\\u0438\\u0442\\u044c \\u0438\\u0437\\u043c\\u0435\\u043d\\u0435\\u043d\\u0438\\u0439. \\u0411\\u0435\\u0441\\u0441\\u043c\\u044b\\u0441\\u043b\\u0435\\u043d\\u043d\\u043e\\u0435 \\u0441\\u043e\\u0434\\u0435\\u0440\\u0436\\u0430\\u043d\\u0438\\u0435. \\u0423\\u0434\\u0430\\u043b\\u0438\\u0442\\u044c. --Ingvar-fed (\\u043e\\u0431\\u0441\\u0443\\u0436\\u0434\\u0435\\u043d\\u0438\\u0435) 20:45, 29 \\u043c\\u0430\\u044f 2012 (UTC) \\u0411\\u0435\\u0441\\u0441\\u043c\\u044b\\u0441\\u043b\\u0435\\u043d\\u043d\\u043e\\u0435 \\u0441\\u043e\\u0434\\u0435\\u0440\\u0436\\u0430\\u043d\\u0438\\u0435. \\u0423\\u0434\\u0430\\u043b\\u0438\\u0442\\u044c. \\u0412\\u043e\\u0437\\u043c\\u043e\\u0436\\u043d\\u043e, \\u043d\\u0430\\u0439\\u0434\\u0443\\u0442\\u0441\\u044f \\u0436\\u0435\\u043b\\u0430\\u044e\\u0449\\u0438\\u0435 \\u043f\\u0435\\u0440\\u0435\\u043f\\u0438\\u0441\\u0430\\u0442\\u044c. --Ingvar-fed (\\u043e\\u0431\\u0441\\u0443\\u0436\\u0434\\u0435\\u043d\\u0438\\u0435) 20:47, 29 \\u043c\\u0430\\u044f 2012 (UTC) \\u041f\\u0435\\u0440\\u0435\\u043f\\u0438\\u0441\\u0430\\u043d\\u0430. --\\u0411\\u0440\\u0430\\u0442\\u044c\\u044f \\u0421\\u0442\\u043e\\u044f\\u043b\\u043e\\u0432\\u044b (\\u0414\\u041c) (\\u043e\\u0431\\u0441\\u0443\\u0436\\u0434\\u0435\\u043d\\u0438\\u0435) 00:08, 12 \\u043d\\u043e\\u044f\\u0431\\u0440\\u044f 2015 (UTC)\",\n    \"category\": [],\n    \"outgoing_link\": [\n        \"\\u0423\\u0447\\u0430\\u0441\\u0442\\u043d\\u0438\\u043a:Ingvar-fed\",\n        \"\\u0423\\u0447\\u0430\\u0441\\u0442\\u043d\\u0438\\u043a:\\u0411\\u0440\\u0430\\u0442\\u044c\\u044f_\\u0421\\u0442\\u043e\\u044f\\u043b\\u043e\\u0432\\u044b_(\\u0414\\u041c)\",\n        \"\\u0422\\u043e\\u0441\\u043a\\u0430\",\n        \"\\u041e\\u0431\\u0441\\u0443\\u0436\\u0434\\u0435\\u043d\\u0438\\u0435_\\u0443\\u0447\\u0430\\u0441\\u0442\\u043d\\u0438\\u043a\\u0430:Ingvar-fed\",\n        \"\\u041e\\u0431\\u0441\\u0443\\u0436\\u0434\\u0435\\u043d\\u0438\\u0435_\\u0443\\u0447\\u0430\\u0441\\u0442\\u043d\\u0438\\u043a\\u0430:\\u0411\\u0440\\u0430\\u0442\\u044c\\u044f_\\u0421\\u0442\\u043e\\u044f\\u043b\\u043e\\u0432\\u044b_(\\u0414\\u041c)\"\n    ],\n    \"timestamp\": \"2015-11-12T05:43:04Z\",\n    \"content_model\": \"wikitext\",\n    \"create_timestamp\": \"2012-05-29T20:45:12Z\",\n    \"defaultsort\": false\n}"
    val left = decode[PageData](json)
    left must beAnInstanceOf[Right[io.circe.Error,PageData]]}

}