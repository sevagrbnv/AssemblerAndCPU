package execute_components

import utils.commandToHex
import java.nio.file.Files
import java.nio.file.Paths

class Compiler {

    fun run(sourceCode: String): List<Int> {
        var program = Files.readAllLines(Paths.get(sourceCode))
        program = removeComments(program)
        program = removeEmptyStrings(program)
        val data = parseData(program)
        program = removeData(program)
        program = parseMarks(program)
        val compiledProgram = mutableListOf<Int>()
        program.forEach { command ->
            compiledProgram.add(commandToCode(command))
        }
        return mergeProgram(data, compiledProgram)
    }

    private fun mergeProgram(data: MutableList<Int>, program: MutableList<Int>): List<Int> {
        val mergedProgram = mutableListOf<Int>()
        program.forEach { command ->
            mergedProgram.add(command)
        }
        if (data.size != 0) {
            mergedProgram.add(0, commandToCode("SETE"))
            mergedProgram.add(1, commandToCode((mergedProgram.size + 1).toString()))
            mergedProgram.addAll(data)
        }
        return mergedProgram
    }

    private fun commandToCode(command: String) = command.commandToHex()

    private fun removeData(program: MutableList<String>): MutableList<String> {
        var currentMark = ""
        var newProgram = mutableListOf<String>()
        program.forEach { line ->
            if (line.isMark()) currentMark = line
            if (currentMark != ".data:") newProgram.add(line)
        }
        return newProgram
    }
    private fun parseData(program: MutableList<String>): MutableList<Int> {
        val data = mutableListOf<Int>()
        if (program.contains(".data:")) {
            var index = program.indexOf(".data:") + 1
            var string = program[index]
            while (!string.isMark()) {
                if (string.contains("ARRAY:")) {
                    string = string.replace("ARRAY:", "")
                    data.addAll(string.split(",").map { it.trim().toInt() })
                }
                string = program[++index]
            }
        }
        return data
    }

    private fun removeEmptyStrings(program: MutableList<String>) =
        program.filter { line ->
            line.trim() != ""
        } as MutableList<String>

    private fun removeComments(program: MutableList<String>): MutableList<String> {
        val cleanedProgram = mutableListOf<String>()
        program.forEach { line ->
            if (line.contains(";"))
                cleanedProgram.add(line.split(";")[0].trim())
            else cleanedProgram.add(line.trim())
        }
        return cleanedProgram
    }

    private fun parseMarks(program: MutableList<String>): MutableList<String> {
        val marks = mutableMapOf<String, Int>()

        program.forEachIndexed { index, line ->
            if (line.startsWith(".") && line.endsWith(":"))
                marks[line.substring(1, line.length - 1)] = index + 2
        }
        program.forEachIndexed { index, line ->
            if (marks.containsKey(line)) {
                program[index] = marks[line].toString()
            }
        }
        program.forEachIndexed { index, line ->
            if (line.isMark())
                program[index] = "0"
        }
        return program
    }
}

fun String.isMark() = this.startsWith(".") && this.endsWith(":")


