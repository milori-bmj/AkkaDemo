name := "AkkaPresentation"

version := "1.0"

scalaVersion := "2.11.4"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.2"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5"

libraryDependencies += "org.reactivemongo" % "reactivemongo_2.11" % "0.10.5.0.akka23"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-remote" % "2.3.10",
  "com.typesafe.akka" %% "akka-actor" % "2.3.10",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.10"
)
