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

object SbtmunitcompilertoolkitPlugin extends AutoPlugin {

  override def trigger = allRequirements
  override def requires = JvmPlugin

  object autoImport {
    val exampleSetting = settingKey[String]("A setting that is automatically imported to the build")
    val exampleTask = taskKey[String]("A task that is automatically imported to the build")
  }

  import autoImport._

  override lazy val projectSettings = Seq(
    exampleSetting := "just an example",
    exampleTask := "computed from example setting: " + exampleSetting.value
  )

  override lazy val buildSettings = Seq()

  override lazy val globalSettings = Seq()
}
