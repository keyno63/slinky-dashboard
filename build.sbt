import sbt.Keys.version

name := "slinky-dashboard"

scalaVersion := "2.13.2"

lazy val packageJson = settingKey[PackageJson]("package.json")

lazy val `slinky-dashboard` = project
  .in(file("."))
  .enablePlugins(ScalaJSBundlerPlugin)
  .enablePlugins(ScalablyTypedConverterPlugin)
  .settings(
    libraryDependencies ++=Seq(
      "me.shadaj" %%% "slinky-web" % "0.6.5",
      "me.shadaj" %%% "slinky-hot" % "0.6.5",
      "org.scalatest" %%% "scalatest" % "3.1.1" % Test
    )
  )
  .settings(settings)

val settings = Def.settings(
  packageJson := PackageJson.readFrom(baseDirectory.value / "package.json"),
  npmDependencies in Compile ++= packageJson.value.dependencies,
  npmDevDependencies in Compile ++= packageJson.value.devDependencies,
  stIgnore := List("react-proxy"),
  scalacOptions += "-Ymacro-annotations",
  version in webpack := "4.43.0",
  version in startWebpackDevServer:= "3.11.0",
  webpackResources := baseDirectory.value / "webpack" * "*",
  webpackConfigFile in fastOptJS := Some(baseDirectory.value / "webpack" / "webpack-fastopt.config.js"),
  webpackConfigFile in fullOptJS := Some(baseDirectory.value / "webpack" / "webpack-opt.config.js"),
  webpackConfigFile in Test := Some(baseDirectory.value / "webpack" / "webpack-core.config.js")
)

webpackDevServerExtraArgs in fastOptJS := Seq("--inline", "--hot")
webpackBundlingMode in fastOptJS := BundlingMode.LibraryOnly()

requireJsDomEnv in Test := true

addCommandAlias("dev", ";fastOptJS::startWebpackDevServer;~fastOptJS")

addCommandAlias("build", "fullOptJS::webpack")
