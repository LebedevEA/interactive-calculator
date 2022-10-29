package el.calculator.core.command

import el.calculator.core.Environment

abstract class Command {
    abstract fun evaluate(environment: Environment): Result

    sealed class Result {
        object Empty : Result()
        object Exit : Result()
        data class Message(val value: String) : Result()
    }
}
