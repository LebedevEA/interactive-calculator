package el.calculator

import el.calculator.core.command.Command

fun interface CommandParser {
    fun parse(input: String): Command
}
