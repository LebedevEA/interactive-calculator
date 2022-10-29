package el.calculator.core.expression

import el.calculator.core.Environment

data class Variable(private val name: String) : Expression() {
    override fun evaluate(environment: Environment): Int {
        return environment.getVariableValue(name)
    }
}
