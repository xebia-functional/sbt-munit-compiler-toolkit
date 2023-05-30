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

import sbt._
object Dependencies {

  object Versions {
    val sbtCiRelease = "1.5.7"
    val sbtGithubActions = "0.13.0"
    val sbtDependencyUpdates = "1.2.7"
    val sbtHeader = "5.9.0"
    val sbtGithub = "0.11.2"
    val sbtGithubMdoc = "0.11.2"
    val sbtMdoc = "2.3.2"
    val sbtScalafmt = "2.4.6"
    val munit = "0.7.29"
  }

  object Compile {
    val `munit-compiler-toolkit-testkit` =
      "com.xebia" %% "munit-compiler-toolkit-testkit" % "0.1.4"
  }

  object SbtPlugins {
    val sbtCiRelease = "com.geirsson" % "sbt-ci-release" % Versions.sbtCiRelease
    val sbtGithubActions =
      "com.codecommit" % "sbt-github-actions" % Versions.sbtGithubActions
    val sbtDependencyUpdates =
      "org.jmotor.sbt" % "sbt-dependency-updates" % Versions.sbtDependencyUpdates
    val sbtHeader = "de.heikoseeberger" % "sbt-header" % Versions.sbtHeader
    val sbtGithub = "com.alejandrohdezma" %% "sbt-github" % Versions.sbtGithub
    val sbtGithubMdoc =
      "com.alejandrohdezma" % "sbt-github-mdoc" % Versions.sbtGithubMdoc
    val sbtMdoc = "org.scalameta" % "sbt-mdoc" % Versions.sbtMdoc
    val sbtScalafmt = "org.scalameta" % "sbt-scalafmt" % Versions.sbtScalafmt
  }

  object Test {
    val munit = "org.scalameta" %% "munit" % Versions.munit
  }

  lazy val dependencies =
    Seq(
      Compile.`munit-compiler-toolkit-testkit`,
      SbtPlugins.sbtCiRelease,
      SbtPlugins.sbtGithubActions,
      SbtPlugins.sbtDependencyUpdates,
      SbtPlugins.sbtHeader,
      SbtPlugins.sbtGithub,
      SbtPlugins.sbtGithubMdoc,
      SbtPlugins.sbtMdoc,
      SbtPlugins.sbtScalafmt,
      Test.munit
    )

}
