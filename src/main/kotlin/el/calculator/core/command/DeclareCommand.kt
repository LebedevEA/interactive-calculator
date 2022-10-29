package el.calculator.core.command

import el.calculator.core.Environment
import el.calculator.core.expression.Expression

data class DeclareCommand(
    private val name: String,
    private val expression: Expression,
) : Command() {
    override fun evaluate(environment: Environment): Result {
        val value = expression.evaluate(environment)
        environment.declareVariable(name, value)
        return Result.Empty
    }
}
