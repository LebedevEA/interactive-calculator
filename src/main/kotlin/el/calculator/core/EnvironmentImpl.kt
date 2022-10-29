package el.calculator.core

import el.calculator.VariableNotDeclaredException

class EnvironmentImpl : Environment {
    private val variableNameToValue: MutableMap<String, Int> = mutableMapOf()

    override fun getVariableValue(name: String): Int {
        return variableNameToValue[name] ?: throw VariableNotDeclaredException(name)
    }

    override fun declareVariable(name: String, value: Int) {
        variableNameToValue[name] = value
    }
}
