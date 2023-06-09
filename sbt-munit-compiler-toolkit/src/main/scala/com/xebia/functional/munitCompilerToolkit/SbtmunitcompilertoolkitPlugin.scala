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

/** sbt plugin object for the SbtMunitCompilerToolkitPlugin.
  *
  * This plugin configures the necessary settings and dependencies for using the
  * MUnit Compiler Toolkit. It adds the required library dependencies, sets up
  * the necessary Java options to pass the classpaths, and configures other
  * project settings. The plugin extends the AutoPlugin trait, which allows it
  * to be automatically enabled in an sbt project.
  *
  * This plugin must be explicitly enabled with
  * `enablePlugins(SbtMunitCompilerToolkitPlugin)`.
  *
  * @see
  *   H * [[https://www.scala-sbt.org/1.x/docs/Plugins.html Sbt Plugins]]
  * @see
  *   [[https://github.com/xebia-functional/munit-compiler-toolkit munit-compiler-toolkit]]
  */
object SbtMunitCompilerToolkitPlugin extends AutoPlugin {

  override def trigger = allRequirements
  override def requires = empty

  private val munitCompilerToolkitTestkitVersion = "0.4.0"
  private val munitSnapVersion = "0.1.6"

  object autoImport {}

  import autoImport._

  override def projectConfigurations: Seq[Configuration] = Seq(IntegrationTest)

  /** This sets the necessary java properties to pass a compiler plugin to
    * `test` and `it`.
    *
    * The key thing to note is that `fork` must be true for the required
    * additional Java system properties to be passed at runtime.
    *
    * @return
    *   The settings to be automatically added to the project.
    */
  override lazy val projectSettings = Defaults.itSettings ++ Seq(
    resolvers += Resolver.sonatypeRepo("public"),
    libraryDependencies += "com.xebia" %% "munit-compiler-toolkit" % munitCompilerToolkitTestkitVersion % s"${Test.name},${IntegrationTest.name}",
    libraryDependencies += "com.xebia" %% "munit-snap" % munitSnapVersion % s"${Test.name},${IntegrationTest.name}",
    libraryDependencies ++= List(
      "org.scala-lang" %% "scala3-compiler" % scalaVersion.value
    ),
    exportJars := true,
    autoAPIMappings := true,
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
    }.value,
    IntegrationTest / fork := true,
    IntegrationTest / javaOptions += {
      val `scala-compiler-classpath` =
        (Compile / dependencyClasspath).value.files
          .map(_.toPath().toAbsolutePath().toString())
          .mkString(":")
      s"-Dscala-compiler-classpath=${`scala-compiler-classpath`}"
    },
    IntegrationTest / javaOptions += {
      s"""-Dcompiler-scalacOptions=\"${scalacOptions.value.mkString(" ")}\""""
    },
    IntegrationTest / javaOptions += Def.taskDyn {
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
