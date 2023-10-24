package execute_components

import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths

class CompilerImpl(path: String) : Compiler {

    private val program: List<String> by lazy {
        Files.readAllLines(Paths.get(path))
    }

    override fun run(): MutableList<String> {
        val compiledProgram = mutableListOf<String>()
        program.forEachIndexed { line, command ->
            val code = if (line % 2 == 0) commandToCode(command) else formatValue(command)
            compiledProgram.add(String.format("0x%04X", code))
        }
        return compiledProgram
    }

    fun commandToCode(command: String) = when (command) {
        "PUSH" -> 0
        "POP" -> 1
        "ADD" -> 2
        "JSPE" -> 3
        "JSNPE" -> 4
        "HLT" -> 10
        "RST" -> 11
        else -> 15
    }

    fun formatValue(value: String) = when (value) {
        "" -> 0
        else -> value.toInt()
    }
}
