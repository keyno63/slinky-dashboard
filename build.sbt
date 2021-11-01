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
      "me.shadaj"     %%% "slinky-react-router" % "0.6.5",
      "net.exoego"    %%% "scala-js-nodejs-v14" % "0.12.0",
      "org.scalatest" %%% "scalatest"           % "3.1.1" % Test
    )
  )
  .settings(settings)

val settings = Def.settings(
  packageJson := PackageJson.readFrom(baseDirectory.value / "package.json"),
  (Compile / npmDependencies) ++= packageJson.value.dependencies,
  (Compile / npmDevDependencies) ++= packageJson.value.devDependencies,
  stIgnore := List("react-proxy"),
  stFlavour := Flavour.Slinky,
  scalacOptions += "-Ymacro-annotations",
  (webpack / version) := "4.43.0",
  (startWebpackDevServer / version) := "3.11.0",
  webpackResources := baseDirectory.value / "webpack" * "*",
  (fastOptJS / webpackConfigFile) := Some(baseDirectory.value / "webpack" / "webpack-fastopt.config.js"),
  (fullOptJS / webpackConfigFile) := Some(baseDirectory.value / "webpack" / "webpack-opt.config.js"),
  (Test / webpackConfigFile) := Some(baseDirectory.value / "webpack" / "webpack-core.config.js")
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
