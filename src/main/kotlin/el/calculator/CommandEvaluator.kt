package el.calculator

import el.calculator.core.Environment
import el.calculator.core.command.Command
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.Writer

class CommandEvaluator(
    private val environment: Environment,
    private val commandParser: CommandParser,
    private val reader: BufferedReader,
    private val writer: BufferedWriter,
) {
    fun run() {
        while (true) {
            writer.write(">>> ", flush = true)
            val line = reader.readLine() ?: break
            try {
                val command = commandParser.parse(line)
                when (val result = command.evaluate(environment)) {
                    Command.Result.Empty -> Unit
                    Command.Result.Exit -> {
                        writer.writeln("Exiting...")
                        break
                    }
                    is Command.Result.Message -> writer.writeln(result.value)
                }
            } catch (ex: CalculatorException) {
                writer.writeln("Error: ${ex.message}")
            }
        }
    }
}

private fun Writer.writeln(str: String, flush: Boolean = true) {
    write("$str\n", flush)
}

private fun Writer.write(str: String, flush: Boolean) {
    write(str)
    if (flush) flush()
}
