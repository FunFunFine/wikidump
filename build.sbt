val Http4sVersion = "0.21.0-M5"
val CirceVersion = "0.12.2"
val Specs2Version = "4.8.0"
val LogbackVersion = "1.2.3"
val MongoDriverVersion = "2.7.0"
val ReactiveMongoVersion = "0.18.8"


lazy val root = (project in file("."))
  .settings(
    organization := "com.voi",
    name := "wikidump",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.0",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s" %% "http4s-circe" % Http4sVersion,
      "org.http4s" %% "http4s-dsl" % Http4sVersion,
      "io.circe" %% "circe-generic" % CirceVersion,
      "io.circe" %% "circe-generic-extras" % CirceVersion,
      "io.circe" %% "circe-parser" % CirceVersion,
      "org.joda" %% "joda-time" % "2.10.5",
      "org.reactivemongo" %% "reactivemongo" % ReactiveMongoVersion,
      "org.specs2" %% "specs2-core" % Specs2Version % "test",
      "ch.qos.logback" % "logback-classic" % LogbackVersion,
      "org.mongodb.scala" %% "mongo-scala-driver" % MongoDriverVersion
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3"),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0")
  )

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  //"-Ypartial-unification",
  "-Xfatal-warnings",
)
