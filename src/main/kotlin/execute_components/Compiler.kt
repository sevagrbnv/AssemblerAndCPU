package execute_components

import exceptions.CompilationError
import utils.toHexFormat
import java.nio.file.Files
import java.nio.file.Paths

class Compiler {

    fun run(sourceCode: String): MutableList<String> {
        val program = Files.readAllLines(Paths.get(sourceCode))
        val compiledProgram = mutableListOf<String>()
        program.forEach { command ->
            val code = commandToCode(command)
            compiledProgram.add(code.toHexFormat())
        }
        return compiledProgram
    }

    private fun commandToCode(command: String) = when (command) {
        "PUSH" -> 0
        "POP"  -> 1
        "ADD"  -> 2
        "SPE"  -> 3
        "SNPE" -> 4
        "J"    -> 5
        "ACC"  -> 6
        "HLT"  -> 10
        "RST"  -> 11
        else   -> try {
            command.toInt()
        } catch (e: java.lang.NumberFormatException) {
            throw CompilationError(command)
        }
    }
}
