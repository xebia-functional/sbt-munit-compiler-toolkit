
name := """sbt-sbt-munit-compiler-toolkit"""
organization := "com.xebia"
version := "0.1-SNAPSHOT"

sbtPlugin := true

// choose a test framework

// utest
//libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test"
//testFrameworks += new TestFramework("utest.runner.Framework")

// ScalaTest
//libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.9" % "test"
//libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test"

// Specs2
//libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "4.12.8" % "test")
//scalacOptions in Test ++= Seq("-Yrangepos")

inThisBuild(List(
  organization := "com.xebia",
  homepage := Some(url("https://github.com/xebia-functional/sbt-munit-compiler-toolkit")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "xebia-functional",
      "Xebia Functional",
      "functional-ops@xebia.com",
      url("https://www.47deg.com/")
    )
  )
))

initialCommands in console := """import com.xebia.functional.munitCompilerToolkit._"""

enablePlugins(ScriptedPlugin)
// set up 'scripted; sbt plugin for testing sbt plugins
scriptedLaunchOpts ++=
  Seq("-Xmx1024M", "-Dplugin.version=" + version.value)

ThisBuild / githubWorkflowTargetTags ++= Seq("v*")
ThisBuild / githubWorkflowPublishTargetBranches :=
  Seq(RefPredicate.StartsWith(Ref.Tag("v")))

ThisBuild / githubWorkflowPublish := Seq(
  WorkflowStep.Sbt(
    List("ci-release"),
    env = Map(
      "PGP_PASSPHRASE" -> "${{ secrets.PGP_PASSPHRASE }}",
      "PGP_SECRET" -> "${{ secrets.PGP_SECRET }}",
      "SONATYPE_PASSWORD" -> "${{ secrets.SONATYPE_PASSWORD }}",
      "SONATYPE_USERNAME" -> "${{ secrets.SONATYPE_USERNAME }}"
    )
  )
)

