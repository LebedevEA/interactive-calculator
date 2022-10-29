package el.calculator

import el.calculator.core.Environment
import el.calculator.core.EnvironmentImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.BufferedWriter
import java.io.StringWriter

class CommandEvaluatorTest {
    @Test
    fun `test corresponds example`() {
        val expectedOutput = """
            >>> 42
            >>> 120
            >>> >>> 15
            >>> >>> 2
            >>> 
        """.trimIndent() // will end after finds out that input it empty
        val userInput = """
            42
            10 * (5 + 7)
            let x = 2 * 7
            x + 1
            let x = x / 5
            x
            
        """.trimIndent()

        val testParser = CommandParser { input ->
            when (input) {
                "42" -> evaluate(int(42))
                "10 * (5 + 7)" -> evaluate(int(10) * ((int(5) + int(7))))
                "let x = 2 * 7" -> declare("x", int(2) * int(7))
                "x + 1" -> evaluate(variable("x") + int(1))
                "let x = x / 5" -> declare("x", variable("x") / int(5))
                "x" -> evaluate(variable("x"))
                else -> Assertions.fail()
            }
        }

        testEvaluatorProduces(
            expectedOutput = expectedOutput,
            userInput = userInput,
            parser = testParser,
        )
    }

    @Test
    fun `test fibonacci with assignment works`() {
        val repeats = 100
        val expectedOutput = buildString {
            repeat(2 + 3 * repeats + 1) {
                append(">>> ")
            }
            append(fibonacci(repeats + 2))
            append("\n>>> ")
        }
        val userInput = buildString {
            append("let fst = 1\n")
            append("let snd = 1\n")
            repeat(repeats) {
                append("let tmp = snd\n")
                append("let snd = fst + snd\n")
                append("let fst = tmp\n")
            }
            append("snd\n")
        }
        val testParser = CommandParser { input ->
            when (input) {
                "let fst = 1" -> declare("fst", int(1))
                "let snd = 1" -> declare("snd", int(1))
                "let tmp = snd" -> declare("tmp", variable("snd"))
                "let snd = fst + snd" -> declare("snd", variable("fst") + variable("snd"))
                "let fst = tmp" -> declare("fst", variable("tmp"))
                "snd" -> evaluate(variable("snd"))
                else -> Assertions.fail()
            }
        }
        testEvaluatorProduces(
            expectedOutput = expectedOutput,
            userInput = userInput,
            parser = testParser,
        )
    }

    @Test
    fun `test prints errors correctly`() {
        val expectedOutput = """
            >>> Error: Cannot divide by zero
            >>> 43
            >>> 
        """.trimIndent()
        val userInput = """
            42 / 0
            43
            
        """.trimIndent()
        val testParser = CommandParser { input ->
            when (input) {
                "42 / 0" -> evaluate(int(42) / int(0))
                "43" -> evaluate(int(43))
                else -> Assertions.fail()
            }
        }
        testEvaluatorProduces(
            expectedOutput = expectedOutput,
            userInput = userInput,
            parser = testParser,
        )
    }

    // first fibonacci number has index 1
    private tailrec fun fibonacci(n: Int, fst: Int = 1, snd: Int = 1): Int {
        require(n >= 1)
        return when (n) {
            1 -> fst
            2 -> snd
            else -> fibonacci(n - 1, snd, fst + snd)
        }
    }

    private fun testEvaluatorProduces(
        expectedOutput: String,
        testEnvironment: Environment = EnvironmentImpl(),
        userInput: String,
        parser: CommandParser,
    ) {
        TestConsole(userInput).use { console ->
            val commandEvaluator = CommandEvaluator(
                environment = testEnvironment,
                commandParser = parser,
                reader = console.input,
                writer = console.output,
            )
            commandEvaluator.run()

            Assertions.assertEquals(expectedOutput, console.outputResult)
        }
    }

    private class TestConsole(userInput: String) : AutoCloseable {
        private val outputStringWriter = StringWriter()
        val outputResult: String get() = outputStringWriter.toString()
        val output: BufferedWriter = outputStringWriter.buffered()
        val input = userInput.reader().buffered()
        override fun close() {
            outputStringWriter.close()
            input.close()
        }
    }
}
