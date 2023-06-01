package example

import com.xebia.functional.munitCompilerToolkit.CompilerSuite
import dotty.tools.dotc.core.Contexts.Context
import munit.SnapshotSuite

class ExampleCompilerSnapshotSuite extends SnapshotSuite, CompilerSuite:

  snapshotTest("The example plugin should add a logging interceptor for all calls"){
    given Context = compilerContext()
    val source = """|def example(s: String): String =
                    |  s
                    |object Thing{
                    |  val x = example("test")
                    |}
                    |""".stripMargin
    compileToStringTreeAndStringContext(source, Option("pickleQuotes"))
  }

end ExampleCompilerSnapshotSuite
