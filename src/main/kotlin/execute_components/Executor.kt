package execute_components

import hexToInt
import provideCPU
import provideCompiler
import provideMemory
import java.nio.file.FileSystems

class Executor {

    private var state: State = State.WAIT
    private val memory = provideMemory()
    private val cpu = provideCPU(memory)

    fun run() {
        val compiler = provideCompiler(PATH)
        val tactCounter = TactCounter()

        val compiledProgram = compiler.run()
        compiledProgram.forEachIndexed { index, string ->
            memory[index] = string
        }

        state = State.READ_COMMAND
        while (state != State.WAIT) {
            state = if (tactCounter.get() == 0) State.READ_COMMAND else State.EXECUTE_COMMAND
            when (state) {
                State.READ_COMMAND -> readCommand(memory)
                State.EXECUTE_COMMAND -> executeCommand(memory)
                else -> throw IllegalStateException()
            }
            cpu.pc.next()
            tactCounter.next()
            cpu.flags.refresh(cpu.gpr, cpu.sp)
            monitor()
            readLine()
        }
    }

    private fun readCommand(program: MutableList<String>) {
        println(program[cpu.pc.value])
        when (program[cpu.pc.value]) {
            /*
                PUSH -> 0x0000
                POP -> 0x0001
                ADD -> 0x0002
                SPE -> 0x0003 // stack is empty
                HLT -> 0x000A
                RST -> 0x000B
            */

            "0x0000" -> { // PUSH
                memory[cpu.sp.add()] = program[cpu.pc.seekNext()]
            }
            "0x0001" -> { // POP
                memory[cpu.sp.pop()] = "0x00"
            }
            "0x0002" -> { // ADD
                val a = cpu.sp.pop()
                cpu.gpr["A"]?.let { cpu.alu.setA(it) }
                cpu.alu.setB(memory[a].hexToInt())
            }
            "0x0003" -> { // JSPE - stack is empty
                if (cpu.flags["JSPE"] == true) {
                    cpu.pc.value = memory[cpu.pc.seekNext()].hexToInt() + JUMP_SHIFT
                }
            }
            "0x0004" -> { // JSNPE - stack is not empty
                if (cpu.flags["JSPE"] == false) {
                    cpu.pc.value = memory[cpu.pc.seekNext()].hexToInt() + JUMP_SHIFT
                }
            }
            "0x000A" -> { // HLT
                state = State.WAIT
            }

            else -> {}
        }

    }

    private fun executeCommand(program: MutableList<String>) {
        when (program[cpu.pc.value - 1]) {
            /*
                PUSH -> 0x0000
                ADD -> 0x0001
                POP -> 0x0002
                SPE -> 0x0003
                HLT -> 0x1010
                RST -> 0x1011
            */

            "0x0002" -> { // ADD
                cpu.alu.calc()
                println(cpu.alu.getRes())
                cpu.gpr["A"] = cpu.alu.getRes()
            }
        }
    }

    fun monitor() {
        println(cpu.gpr)
        println()
        println(memory.toString())
        println(cpu.flags)
        println(cpu.sp)
        println(cpu.pc)
        println("----------------------------------------")
    }

    companion object {
        const val JUMP_SHIFT = -3

        val PATH = "${getRootDirectory()}\\src\\main\\kotlin\\program"
        fun getRootDirectory() = FileSystems.getDefault().getPath("").toAbsolutePath()
    }
}
