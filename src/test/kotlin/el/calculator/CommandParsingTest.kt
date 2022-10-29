package el.calculator

import el.calculator.core.command.Command
import el.calculator.core.expression.Expression
import el.calculator.core.expression.Variable
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class CommandParsingTest {
    companion object {
        @JvmStatic
        fun commandTextToAst() = listOf(
            "0" to evaluate(int(0)),
            "1" to evaluate(int(1)),
            "XAE_A12" to evaluate(variable("XAE_A12")),
            "1 + 2 * 3" to evaluate(int(1) + (int(2) * int(3))),
            "2 * 3 + 1" to evaluate((int(2) * int(3) + int(1))),
            "1 + 2 * 3 + 4" to evaluate((int(1) + (int(2) * int(3))) + int(4)),
            "(1 + 2) / 3 + 4" to evaluate(((int(1) + int(2)) / int(3)) + int(4)),
            "(1 + 2) / (3 - 4)" to evaluate((int(1) + int(2)) / (int(3) - int(4))),
            "-x" to evaluate(minus(variable("x"))),
            "-x + 1" to evaluate(minus(variable("x")) + int(1)),
            "-(x +   1)" to evaluate(minus(variable("x") + int(1))),
            "(-(-(-(-x))))" to evaluate(minus(minus(minus(minus(variable("x")))))),
            "(((((((((((((((((((((((((((-43)))))))))))))))))))))))))))" to evaluate(minus(int(43))),
            "h1 +   h2 + h3 + h4 + h5 + h6" to evaluate(
                listOf("h1", "h2", "h3", "h4", "h5", "h6")
                    .map { variable(it) }
                    .reduce<Expression, Variable> { l, r -> l + r }
            ),
            "h1 + (h2 + (h3 +    (h4 + (h5 + h6))))" to evaluate(
                listOf("h1", "h2", "h3", "h4", "h5", "h6")
                    .map { variable(it) }
                    .reduceRight<Expression, Variable> { l, r -> l + r }
            ),
            "let x = -7" to declare("x", minus(int(7))),
            "let LONGER_NAME = -7 * 8 + 10000 + 10000" to declare(
                "LONGER_NAME",
                ((minus(int(7)) * int(8)) + int(10000)) + int(10000)
            ),
            "let abc = 0" to declare("abc", int(0)),
        ).map { Arguments.of(it.first, it.second) }
    }

    // TODO: failing

    @ParameterizedTest
    @MethodSource("commandTextToAst")
    fun `test parser works correctly`(input: String, expectedAst: Command) {
        val actualAst = DefaultCommandGrammar.parse(input)
        Assertions.assertEquals(expectedAst, actualAst)
    }
}
