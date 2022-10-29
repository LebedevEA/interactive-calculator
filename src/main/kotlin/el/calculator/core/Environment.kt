package el.calculator.core

interface Environment {
    fun getVariableValue(name: String): Int
    fun declareVariable(name: String, value: Int)
}
