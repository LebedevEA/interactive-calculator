package el.calculator.core.expression

import el.calculator.core.Environment

data class Multiplication(
    private val left: Expression,
    private val right: Expression,
) : Expression() {
    override fun evaluate(environment: Environment): Int {
        return left.evaluate(environment) * right.evaluate(environment)
    }
}
