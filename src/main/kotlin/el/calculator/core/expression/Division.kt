package el.calculator.core.expression

import el.calculator.DivisionByZeroException
import el.calculator.core.Environment

data class Division(
    private val left: Expression,
    private val right: Expression,
) : Expression() {
    override fun evaluate(environment: Environment): Int {
        val leftResult = left.evaluate(environment)
        val rightResult = right.evaluate(environment)
        if (rightResult == 0) {
            throw DivisionByZeroException()
        }
        return leftResult / rightResult
    }
}
