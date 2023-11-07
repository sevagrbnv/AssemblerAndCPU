package execute_components

import provideCPU
import provideMemory
import utils.hexToCommand
import utils.hexToInt
import utils.toHexFormat

class Executor {

    private var state: State = State.WAIT
    private val memory = provideMemory()
    private val cpu = provideCPU(memory)

    fun run(compiledProgram: MutableList<String>) {
        compiledProgram.forEachIndexed { index, string ->
            memory[index] = string
        }
        state = State.RUN
        while (state != State.WAIT) {
            val command = memory[cpu.pc.value]
            executeCommand(command)
            cpu.pc.next()
            cpu.flags.refresh(cpu.gpr, cpu.sp)
            printState()
            readLine()
        }
    }

    private fun executeCommand(command: String) {
        println(command.hexToCommand())
        when (command) {
            "0x0000" -> { // PUSH
                cpu.pc.next()
                memory[cpu.sp.add()] = memory[cpu.pc.value]
            }
            "0x0001" -> { // POP
                memory[cpu.sp.pop()] = "0x0000"
            }
            "0x0002" -> { // ADD
                val a = cpu.sp.pop()
                cpu.gpr["A"]?.let { cpu.alu.setA(it) }
                cpu.alu.setB(memory[a].hexToInt())
                cpu.alu.calc()
                cpu.gpr["A"] = cpu.alu.getRes()
                memory[cpu.sp.seek()] = 0.toHexFormat()
            }
            "0x0003" -> { // SPE - stack is empty
                cpu.pc.next()
                print(memory[cpu.pc.value])
                if (cpu.flags["SPE"] == true)
                    cpu.pc.value = memory[cpu.pc.value].hexToInt() + JUMP_SHIFT
            }
            "0x0004" -> { // SNPE - stack is not empty
                cpu.pc.next()
                print(memory[cpu.pc.value])
                if (cpu.flags["SPE"] == false)
                    cpu.pc.value = memory[cpu.pc.value].hexToInt() + JUMP_SHIFT
            }
            "0x0006" -> { // ACC
                cpu.pc.next()
                cpu.gpr["A"] = memory[cpu.sp.pop()].toInt()
            }
            "0x000A" -> { // HLT
                state = State.WAIT
            }
            "0x000B" -> { // RST
                memory.reset()
                cpu.alu.reset()
                cpu.gpr.reset()
                cpu.pc.reset()
                cpu.sp.reset()
                cpu.flags.reset()
            }
        }

    }

    private fun printState() {
        println(cpu.gpr)
        println()
        println(memory.toString())
        println(cpu.flags)
        println(cpu.sp)
        println(cpu.pc)
        println("----------------------------------------")
    }

    companion object {
        const val JUMP_SHIFT = -2
    }
}
