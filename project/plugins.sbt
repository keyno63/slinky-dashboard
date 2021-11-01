addSbtPlugin("org.scala-js"  % "sbt-scalajs"         % "1.3.0")
addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.20.0")
addSbtPlugin("org.scalameta" % "sbt-scalafmt"        % "2.4.2")

addSbtPlugin("org.scalablytyped.converter" % "sbt-converter" % "1.0.0-beta34")

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "upickle" % "1.2.2"
)
