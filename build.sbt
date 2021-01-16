import sbt.Keys.version

fork := true

name := "slinky-dashboard"

scalaVersion := "2.13.2"

lazy val packageJson = settingKey[PackageJson]("package.json")

lazy val `slinky-dashboard` = project
  .in(file("."))
  .enablePlugins(ScalaJSBundlerPlugin)
  .enablePlugins(ScalablyTypedConverterPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "me.shadaj"     %%% "slinky-web"          % "0.6.5",
      "me.shadaj"     %%% "slinky-hot"          % "0.6.5",
      "org.scalatest" %%% "scalatest"           % "3.1.1" % Test
    )
  )
  .settings(settings)

val settings = Def.settings(
  packageJson := PackageJson.readFrom(baseDirectory.value / "package.json"),
  npmDependencies in Compile ++= packageJson.value.dependencies,
  npmDevDependencies in Compile ++= packageJson.value.devDependencies,
  stIgnore := List("react-proxy"),
  stFlavour := Flavour.Slinky,
  scalacOptions += "-Ymacro-annotations",
  version in webpack := "4.43.0",
  version in startWebpackDevServer := "3.11.0",
  webpackResources := baseDirectory.value / "webpack" * "*",
  webpackConfigFile in fastOptJS := Some(baseDirectory.value / "webpack" / "webpack-fastopt.config.js"),
  webpackConfigFile in fullOptJS := Some(baseDirectory.value / "webpack" / "webpack-opt.config.js"),
  webpackConfigFile in Test := Some(baseDirectory.value / "webpack" / "webpack-core.config.js")
)

lazy val scraping = project
  .in(file("./scrap"))
  .settings(
    name := "scraping",
    libraryDependencies ++= Seq(
      "net.ruippeixotog" %% "scala-scraper" % "2.2.0"
    ) ++ Seq(
      "org.skinny-framework" %% "skinny-http-client"
    ).map(_ % "3.1.0") ++ Seq(
      "org.slf4j" % "slf4j-log4j12" % "1.7.30" % Test,
      "log4j"     % "log4j"         % "1.2.17"
    )
  )

webpackDevServerExtraArgs in fastOptJS := Seq("--inline", "--hot")
webpackBundlingMode in fastOptJS := BundlingMode.LibraryOnly()

requireJsDomEnv in Test := true

addCommandAlias("dev", ";fastOptJS::startWebpackDevServer;~fastOptJS")

addCommandAlias("build", "fullOptJS::webpack")

// scalafmt command alias.
addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias(
  "check",
  "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck"
)
