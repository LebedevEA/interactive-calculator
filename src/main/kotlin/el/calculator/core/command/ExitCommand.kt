package el.calculator.core.command

import el.calculator.core.Environment

object ExitCommand : Command() {
    override fun evaluate(environment: Environment) = Result.Exit
}
