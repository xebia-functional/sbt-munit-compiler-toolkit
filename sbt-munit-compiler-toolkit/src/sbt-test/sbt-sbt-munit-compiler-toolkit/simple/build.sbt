ThisBuild / scalaVersion := "3.2.2"

lazy val root = project.in(file("."))
  .settings(publish / skip := true)
  .aggregate(pluginLib, compilerPlugin)

lazy val pluginLib = project
  .in(file("./pluginLib"))
  .settings(name := "example-plugin-lib")

lazy val compilerPlugin = project.in(file("./plugin"))
  .settings(
    name := "example-plugin"
  )
  .dependsOn(pluginLib)
  .enablePlugins(SbtMunitCompilerToolkitPlugin)
  
