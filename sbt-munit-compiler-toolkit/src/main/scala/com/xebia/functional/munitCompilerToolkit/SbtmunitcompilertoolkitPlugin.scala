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

package com.xebia.functional.munitCompilerToolkit

import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin

object SbtMunitCompilerToolkitPlugin extends AutoPlugin {

  override def trigger = allRequirements
  override def requires = JvmPlugin

  private val munitCompilerToolkitTestkitVersion = "0.1.4"

  object autoImport {
  }

  import autoImport._

  override lazy val projectSettings = Seq(
    resolvers += Resolver.sonatypeRepo("public"),
    libraryDependencies += "com.xebia" %% "munit-compiler-toolkit-testkit" % munitCompilerToolkitTestkitVersion,
    libraryDependencies ++= List(
      "org.scala-lang" %% "scala3-compiler" % scalaVersion.value
    ),
    exportJars := true,
    autoAPIMappings := true,
    publish / skip := true,
    Test / fork := true,
    Test / javaOptions += {
      val `scala-compiler-classpath` =
        (Compile / dependencyClasspath).value.files
          .map(_.toPath().toAbsolutePath().toString())
          .mkString(":")
      s"-Dscala-compiler-classpath=${`scala-compiler-classpath`}"
    },
    Test / javaOptions += {
      s"""-Dcompiler-scalacOptions=\"${scalacOptions.value.mkString(" ")}\""""
    },
    Test / javaOptions += Def.taskDyn {
      Def.task {
        val _ = (Compile / Keys.`package`).value
        val `scala-compiler-options` =
          s"${(Compile / packageBin).value}"
        s"""-Dscala-compiler-plugin=${`scala-compiler-options`}"""
      }
    }.value
  )

  override lazy val buildSettings = Seq()

  override lazy val globalSettings = Seq()
}
