package el.calculator

import el.calculator.core.EnvironmentImpl

fun main() {
    try {
        CommandEvaluator(
            environment = EnvironmentImpl(),
            commandParser = DefaultCommandGrammar,
            reader = System.`in`.bufferedReader(),
            writer = System.out.bufferedWriter(),
        ).run()
    } catch (e: Exception) {
        println("Error: unhandled error, exiting...")
    }
}
