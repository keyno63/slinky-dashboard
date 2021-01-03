import sbt.Keys.version

name := "slinky-dashboard"

scalaVersion := "2.13.2"

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
  npmDependencies in Compile ++= Seq(
    // ui
    "@material-ui/core"   -> "3.9.4", // note: version 4 is not supported yet
    "@material-ui/styles" -> "3.0.0-alpha.10", // note: version 4 is not supported yet
    "@material-ui/icons"  -> "3.0.2",
    // typesprict
    "@types/prop-types" -> "15.7.3",
    "@types/react" -> "16.9.42",
    "@types/react-dom" -> "16.9.8",
    "@types/react-router-dom" -> "5.1.2",
    // react
    "react" -> "16.13.1",
    "react-dom" -> "16.13.1",
    "react-proxy" -> "1.1.8",
    "react-router-dom" -> "5.1.2"
  ),
  npmDevDependencies in Compile ++= Seq(
    "file-loader" -> "6.0.0",
    "style-loader" -> "1.2.1",
    "css-loader" -> "3.5.3",
    "html-webpack-plugin" -> "4.3.0",
    "copy-webpack-plugin" -> "5.1.1",
    "webpack-merge" -> "4.2.2"
  ),
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
