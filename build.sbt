name := "transcode-service"

version := "1.0"

scalaVersion := "2.12.3"

val versions = new {
  val FINATRA = "17.10.0"
  val SCALA_CACHE = "0.9.4"
}

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % versions.FINATRA,
  "com.github.cb372" %% "scalacache-core" % versions.SCALA_CACHE,
  "com.github.cb372" %% "scalacache-guava" % versions.SCALA_CACHE,
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "net.debasishg" %% "redisclient" % "3.4",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.typesafe" % "config" % "1.3.1",
  "com.squareup.okhttp3" % "okhttp" % "3.8.1",
  "com.squareup.okio" % "okio" % "1.13.0",
  "commons-codec" % "commons-codec" % "1.10",
  ("com.qcloud" % "cos_api" % "4.4").exclude("org.slf4j" ,"slf4j-log4j12")
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8", "-feature"
)

resolvers ++= Seq(
  "Aliyun" at "http://maven.aliyun.com/nexus/content/groups/public",
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  "twttr maven" at "http://maven.twttr.com"
)