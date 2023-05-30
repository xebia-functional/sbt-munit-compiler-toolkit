package example

import com.xebia.functional.munitCompilerToolkit.CompilerSuite


class PluginTestSuite extends CompilerSuite:

  compilerTest("The plugin should input the example plugin scala compiler classpath and transform this source correctly")(
    """|def example(s: String): String =
       |  s
       |object Thing {
       | val x = example("test")
       |}
       |""".stripMargin,
    Option("pickelQuotes")
  ) { case (tree, given Context) =>
    assertEquals(
      cleanCompilerOutput(tree),
      """|package <empty> {
         |  final lazy module val Thing: Thing = new Thing()
         |  @SourceFile("compileFromStringscala") final module class Thing() extends Object() { this: Thing.type =>
         |    private def writeReplace(): AnyRef = new scala.runtime.ModuleSerializationProxy(classOf[Thing.type])
         |    val x: String = new com.xebia.functional.munitCompilerToolkit.LoggingInterceptor[String](example("test")).apply("example")(["test" : Any]*)
         |  }
         |  final lazy module val compileFromStringpackage:
         |    compileFromStringpackage
         |   = new compileFromStringpackage()
         |  @SourceFile("compileFromStringscala") final module class
         |    compileFromStringpackage
         |  () extends Object() { this: compileFromStringpackage.type =>
         |    private def writeReplace(): AnyRef =
         |      new scala.runtime.ModuleSerializationProxy(classOf[compileFromStringpackage.type])
         |    def example(s: String): String = s
         |  }
         |}""".stripMargin
    )
  }
end PluginTestSuite
