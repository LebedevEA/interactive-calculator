package el.calculator.core.expression

import el.calculator.core.Environment

abstract class Expression {
    abstract fun evaluate(environment: Environment): Int
}
