package el.calculator.core.expression

import el.calculator.core.Environment

data class Integer(private val value: Int) : Expression() {
    override fun evaluate(environment: Environment): Int {
        return value
    }
}
