package el.calculator

import com.github.h0tk3y.betterParse.combinators.leftAssociative
import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.combinators.or
import com.github.h0tk3y.betterParse.combinators.times
import com.github.h0tk3y.betterParse.combinators.unaryMinus
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.grammar.parser
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.Parser
import el.calculator.core.command.Command
import el.calculator.core.command.DeclareCommand
import el.calculator.core.command.EvaluateCommand
import el.calculator.core.command.ExitCommand
import el.calculator.core.expression.Addition
import el.calculator.core.expression.Division
import el.calculator.core.expression.Expression
import el.calculator.core.expression.Integer
import el.calculator.core.expression.Multiplication
import el.calculator.core.expression.Negate
import el.calculator.core.expression.Subtraction
import el.calculator.core.expression.Variable
import java.lang.Exception

object DefaultCommandGrammar : CommandParser {
    private val commandGrammar = object : Grammar<Command>() {
        val variable by regexToken("""(?!let|mut|exit)[a-zA-Z_][a-zA-Z\d_]*""")

        val plus by literalToken("+")
        val minus by literalToken("-")
        val star by literalToken("*")
        val slash by literalToken("/")

        val eq by literalToken("=")

        val leftParenthesis by literalToken("(")
        val rightParenthesis by literalToken(")")

        val nat by regexToken("""0|[1-9]\d*""")

        val let by literalToken("let")
        val mut by literalToken("mut")
        val exit by literalToken("exit")

        @Suppress("unused")
        val whitespace by regexToken("""\s+""", ignore = true)

        val clause: Parser<Expression> by (variable map { Variable(it.text) }) or
            (nat map { Integer(it.text.toInt()) }) or
            (-leftParenthesis * parser(this::expr) * -rightParenthesis) or
            (-minus * parser(this::clause) map { Negate(it) })

        val firstPriorityExpr by leftAssociative(clause, star or slash) { left, operator, right ->
            when (operator.type) {
                star -> Multiplication(left, right)
                slash -> Division(left, right)
                else -> error("unreachable code")
            }
        }

        val expr by leftAssociative(firstPriorityExpr, plus or minus) { left, operator, right ->
            when (operator.type) {
                plus -> Addition(left, right)
                minus -> Subtraction(left, right)
                else -> error("unreachable code")
            }
        }

        override val rootParser: Parser<Command> by (exit map { ExitCommand }) or
            (
                -let * variable * -eq * expr map { (name, expression) ->
                    DeclareCommand(name.text, expression)
                }
                ) or
            (expr map { EvaluateCommand(it) })
    }

    override fun parse(input: String): Command {
        try {
            return commandGrammar.parseToEnd(input)
        } catch (_: Exception) {
            throw ParsingException()
        }
    }
}
