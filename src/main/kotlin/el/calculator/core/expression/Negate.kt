package el.calculator.core.expression

import el.calculator.core.Environment

data class Negate(private val expression: Expression) : Expression() {
    override fun evaluate(environment: Environment): Int {
        return -expression.evaluate(environment)
    }
}
