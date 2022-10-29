package el.calculator.core.command

import el.calculator.core.Environment
import el.calculator.core.expression.Expression

data class EvaluateCommand(private val expression: Expression) : Command() {
    override fun evaluate(environment: Environment): Result {
        val evaluated = expression.evaluate(environment)
        return Result.Message(evaluated.toString())
    }
}
