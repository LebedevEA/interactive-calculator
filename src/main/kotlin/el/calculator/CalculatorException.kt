package el.calculator

import java.lang.Exception

abstract class CalculatorException(message: String) : Exception(message)

class VariableNotDeclaredException(variableName: String) : CalculatorException(
    """"Variable "$variableName" is not declared"""
)

class DivisionByZeroException : CalculatorException(
    """Cannot divide by zero"""
)

class ParsingException : CalculatorException(
    """Could not parse given input"""
)
