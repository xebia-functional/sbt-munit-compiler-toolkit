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
    val (tree, compiledSource, context) = compileToStringTreeAndTreeSourcessAndStringContext(source, Option("pickleQuotes"))
    val pattern = """dotty\.tools\.dotc\.core\.Scopes\$MutableScope@.{0,8}""".r
    val emptyScopePattern = """dotty\.tools\.dotc\.core\.Scopes\$EmptyScope\$@.{0,8}""".r
    val strippedMutableScopes = pattern.replaceAllIn(context, "dotty.tools.dotc.core.Scopes.MutableScope@")
    val strippedEmptyScopes = emptyScopePattern.replaceAllIn(strippedMutableScopes, "dotty.tools.dotc.core.ScopesEmptyScope@")
    (
      pattern.replaceAllIn(tree, "dotty.tools.dotc.core.Scopes.MutableScope@"),
      source,
      strippedEmptyScopes
    )
  }

end ExampleCompilerSnapshotSuite
