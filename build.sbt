/*
 * Copyright 2023 Xebia Functional
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import Dependencies.Compile._
import Dependencies.Test._
ThisBuild / version := "0.1.7"

addCommandAlias(
  "ci-test",
  "scalafmtCheckAll; scalafmtSbtCheck; github; documentation / mdoc; scripted"
)
addCommandAlias("ci-docs", "github; documentation / mdoc")
addCommandAlias("ci-publish", "github; ci-release")

lazy val commonSettings = Seq(
  organizationName := "Xebia Functional",
  startYear := Some(2023)
)

lazy val root = project
  .in(file("."))
  .settings(commonSettings)
  .settings(
    name := "sbt-munit-compiler-toolkit-root",
    publish / skip := true
  )
  .aggregate(`sbt-munit-compiler-toolkit`, `documentation`)

lazy val `sbt-munit-compiler-toolkit` = project
  .in(file("./sbt-munit-compiler-toolkit"))
  .settings(commonSettings)
  .settings(
    name := """sbt-munit-compiler-toolkit""",
    sbtPlugin := true,
    libraryDependencies += munit % Test,
    console / initialCommands := """import com.xebia.functional.munitCompilerToolkit._""",
    scriptedLaunchOpts ++=
      Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
  )
  .enablePlugins(ScriptedPlugin)

lazy val documentation = project
  .dependsOn(`sbt-munit-compiler-toolkit`)
  .settings(commonSettings)
  .enablePlugins(MdocPlugin)
  .settings(mdocOut := file("."))
  .settings(publish / skip := true)

ThisBuild / organization := "com.xebia"
ThisBuild / homepage := Some(
  url("https://github.com/xebia-functional/sbt-munit-compiler-toolkit")
)
ThisBuild / licenses := List(
  "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
)
ThisBuild / developers := List(
  Developer(
    "xebia-functional",
    "Xebia Functional",
    "functional-ops@xebia.com",
    url("https://www.47deg.com/")
  )
)
ThisBuild / githubWorkflowTargetTags ++= Seq("v*")
ThisBuild / githubWorkflowPublishTargetBranches :=
  Seq(RefPredicate.StartsWith(Ref.Tag("v")))
ThisBuild / githubWorkflowPublish := Seq(
  WorkflowStep.Sbt(
    List("ci-release"),
    env = Map(
      "PGP_PASSPHRASE" -> "${{ secrets.PGP_PASSPHRASE }}",
      "PGP_SECRET" -> "${{ secrets.PGP_SECRET }}",
      "SONATYPE_PASSWORD" -> "${{ secrets.XEB_SONATYPE_PASSWORD }}",
      "SONATYPE_USERNAME" -> "${{ secrets.XEB_SONATYPE_USERNAME }}"
    )
  )
)
