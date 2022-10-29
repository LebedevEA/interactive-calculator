package el.calculator

import el.calculator.core.command.DeclareCommand
import el.calculator.core.command.EvaluateCommand
import el.calculator.core.expression.Addition
import el.calculator.core.expression.Division
import el.calculator.core.expression.Expression
import el.calculator.core.expression.Integer
import el.calculator.core.expression.Multiplication
import el.calculator.core.expression.Negate
import el.calculator.core.expression.Subtraction
import el.calculator.core.expression.Variable

fun evaluate(expression: Expression) = EvaluateCommand(expression)
fun declare(name: String, expression: Expression) = DeclareCommand(name, expression)
fun int(value: Int) = Integer(value)
fun variable(name: String) = Variable(name)
fun minus(expression: Expression) = Negate(expression)
infix operator fun Expression.plus(that: Expression) = Addition(this, that)
infix operator fun Expression.minus(that: Expression) = Subtraction(this, that)
infix operator fun Expression.times(that: Expression) = Multiplication(this, that)
infix operator fun Expression.div(that: Expression) = Division(this, that)
