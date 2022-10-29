package el.calculator

import el.calculator.core.Environment
import el.calculator.core.EnvironmentImpl
import el.calculator.core.expression.Expression
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ExpressionTest {
    @Test
    fun `test addition`() {
        Assertions.assertEquals(0, (zero + zero).evaluate())
        Assertions.assertEquals(1, (zero + one).evaluate())
        Assertions.assertEquals(2, (zero + two).evaluate())
        Assertions.assertEquals(3, (zero + three).evaluate())

        Assertions.assertEquals(2, (one + one).evaluate())
        Assertions.assertEquals(4, (two + two).evaluate())

        Assertions.assertEquals(5, (two + three).evaluate())
    }

    @Test
    fun `test subtraction`() {
        Assertions.assertEquals(0, (zero - zero).evaluate())
        Assertions.assertEquals(-1, (zero - one).evaluate())
        Assertions.assertEquals(-2, (zero - two).evaluate())
        Assertions.assertEquals(-3, (zero - three).evaluate())

        Assertions.assertEquals(0, (one - one).evaluate())
        Assertions.assertEquals(0, (two - two).evaluate())

        Assertions.assertEquals(-1, (two - three).evaluate())
        Assertions.assertEquals(1, (three - two).evaluate())
    }

    @Test
    fun `test multiplication`() {
        Assertions.assertEquals(0, (zero * zero).evaluate())
        Assertions.assertEquals(0, (zero * one).evaluate())
        Assertions.assertEquals(0, (zero * two).evaluate())
        Assertions.assertEquals(0, (zero * three).evaluate())

        Assertions.assertEquals(1, (one * one).evaluate())
        Assertions.assertEquals(4, (two * two).evaluate())

        Assertions.assertEquals(6, (two * three).evaluate())
        Assertions.assertEquals(6, (three * two).evaluate())
    }

    @Test
    fun `test division`() {
        Assertions.assertThrowsExactly(DivisionByZeroException::class.java) { (zero / zero).evaluate() }
        Assertions.assertThrowsExactly(DivisionByZeroException::class.java) { (three / zero).evaluate() }
        Assertions.assertEquals(0, (zero / one).evaluate())
        Assertions.assertEquals(0, (zero / two).evaluate())

        Assertions.assertEquals(1, (one / one).evaluate())
        Assertions.assertEquals(1, (two / two).evaluate())

        Assertions.assertEquals(0, (two / three).evaluate())
        Assertions.assertEquals(1, (three / two).evaluate())
        Assertions.assertEquals(2, (four / two).evaluate())
    }

    @Test
    fun `test negate`() {
        Assertions.assertEquals(0, minus(zero).evaluate())
        Assertions.assertEquals(-2, minus(two).evaluate())
    }

    @Test
    fun `test integer`() {
        Assertions.assertEquals(1111, int(1111).evaluate())
    }

    @Test
    fun `test variable`() {
        Assertions.assertEquals(42, variable("a").evaluate(env("a" to 42)))
        Assertions.assertThrowsExactly(VariableNotDeclaredException::class.java) {
            variable("a").evaluate(EnvironmentImpl())
        }
    }

    companion object {
        private fun intExpression(value: Int) = object : Expression() {
            override fun evaluate(environment: Environment) = value
        }

        val zero = intExpression(0)
        val one = intExpression(1)
        val two = intExpression(2)
        val three = intExpression(3)
        val four = intExpression(4)

        private val fakeEnv = object : Environment {
            override fun getVariableValue(name: String): Int = Assertions.fail()
            override fun declareVariable(name: String, value: Int): Unit = Assertions.fail()
        }

        fun Expression.evaluate(): Int = evaluate(fakeEnv)

        fun env(vararg vars: Pair<String, Int>) = object : Environment {
            val map = vars.toMap()
            override fun getVariableValue(name: String) = map[name] ?: Assertions.fail()
            override fun declareVariable(name: String, value: Int): Unit = Assertions.fail()
        }
    }
}
