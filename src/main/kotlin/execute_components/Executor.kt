package execute_components

import provideCPU
import provideMemory
import utils.codeToCommand
import utils.toHexFormat

class Executor {

    private var state: State = State.WAIT
    private val memory = provideMemory()
    private val cpu = provideCPU(memory)

    fun run(compiledProgram: List<Int>) {
        compiledProgram.forEachIndexed { index, commandCode ->
            memory[index] = commandCode
        }
        state = State.RUN
        while (state != State.WAIT) {
            val command = memory[cpu.pc.value]
            executeCommand(command)
            cpu.pc.next()
            cpu.flags.refresh(cpu.alu, cpu.sp)
            printState(command)
            readLine()
        }
    }

    private fun executeCommand(command: Int) {
        when (command.toHexFormat()) {
            "0x0000" -> { //"NOPE"
            }
            "0xFFFF" -> { // "HLT"
                state = State.WAIT
            }

            "0x0001" -> { //"ADD"
                cpu.gpr["A"] = memory[cpu.sp.pop()]
                cpu.gpr["B"] = memory[cpu.sp.pop()]
                cpu.alu.setA(cpu.gpr["A"] ?: 0)
                cpu.alu.setB(cpu.gpr["B"] ?: 0)
                cpu.alu.add()
                cpu.gpr["A"] = cpu.alu.getRes()
                memory[cpu.sp.add()] = cpu.gpr["A"]!!
            }
            "0x0002" -> { //"CMP"
                cpu.gpr["B"] = memory[cpu.sp.pop()]
                cpu.gpr["A"] = memory[cpu.sp.pop()]
                cpu.gpr["A"]?.let { cpu.alu.setA(it) }
                cpu.gpr["B"]?.let { cpu.alu.setB(it) }
                cpu.alu.cmp()
                print(cpu.alu.getRes())
            }

            "0x0F00" -> { //"RMEM"
                val a = memory[cpu.sp.pop()]
                memory[cpu.sp.add()] = memory[a]
            }

            "0x0010" -> {//"J"
                cpu.pc.next()
                cpu.pc.value = memory[cpu.pc.value]
            }
            "0x0011" -> {//"JNNF"
                cpu.pc.next()
                if (cpu.flags["SF"] == true)
                    cpu.pc.value = memory[cpu.pc.value]
            }
            "0x0012" -> { //"JNZ"
                cpu.pc.next()
                if (cpu.flags["ZF"] == true)
                    cpu.pc.value = memory[cpu.pc.value]
            }

            "0x0100" -> { //"RSTA"
                cpu.gpr["A"] = 0
            }
            "0x0101" -> { //"RSTB"
                cpu.gpr["B"] = 0
            }
            "0x0102" -> { //"RSTC"
                cpu.gpr["C"] = 0
            }
            "0x0103" -> { //"RSTD"
                cpu.gpr["D"] = 0
            }
            "0x0104" -> { //"RSTE"
                cpu.gpr["E"] = 0
            }
            "0x0105" -> { //"RSTF"
                cpu.gpr["F"] = 0
            }

            "0x0200" -> { //"PUSHA"
                memory[cpu.sp.add()] = cpu.gpr["A"]!!
            }
            "0x0201" -> { //"PUSHB"
                memory[cpu.sp.add()] = cpu.gpr["B"]!!
            }
            "0x0202" -> { //"PUSHC"
                memory[cpu.sp.add()] = cpu.gpr["C"]!!
            }
            "0x0203" -> { //"PUSHD"
                memory[cpu.sp.add()] = cpu.gpr["D"]!!
            }
            "0x0204" -> { //"PUSHE"
                memory[cpu.sp.add()] = cpu.gpr["E"]!!
            }
            "0x0205" -> { //"PUSHF"
                memory[cpu.sp.add()] = cpu.gpr["F"]!!
            }

            "0x0300" -> { // "POPA"
                cpu.gpr["A"]?.let { memory[cpu.sp.pop()] }
                memory[cpu.sp.seek()] = 0
            }
            "0x0301" -> { // "POPB"
                cpu.gpr["B"] = memory[cpu.sp.pop()]
                memory[cpu.sp.seek()] = 0
            }
            "0x0302" -> { // "POPC"
                cpu.gpr["C"]?.let { memory[cpu.sp.pop()] }
                memory[cpu.sp.seek()] = 0
            }
            "0x0303" -> { // "POPD"
                cpu.gpr["D"]?.let { memory[cpu.sp.pop()] }
                memory[cpu.sp.seek()] = 0
            }
            "0x0304" -> { // "POPE"
                cpu.gpr["E"]?.let { memory[cpu.sp.pop()] }
                memory[cpu.sp.seek()] = 0
            }
            "0x0305" -> { // "POPF"
                cpu.gpr["F"]?.let { memory[cpu.sp.pop()] }
                memory[cpu.sp.seek()] = 0
            }

            "0x0400" -> { //"INCA"
                cpu.alu.setA(cpu.gpr["A"]!!)
                cpu.alu.setB(1)
                cpu.alu.add()
                cpu.gpr["A"] = cpu.alu.getRes()
            }
            "0x0401" -> { //"INCB"
                memory[cpu.sp.add()] = cpu.gpr["B"] ?: 0
                cpu.gpr["A"] = memory[cpu.sp.pop()]
                cpu.alu.setA(cpu.gpr["A"] ?: 0)
                cpu.alu.setB(1)
                cpu.alu.add()
                cpu.gpr["A"] = cpu.alu.getRes()
                memory[cpu.sp.add()] = cpu.gpr["A"] ?: 0
                cpu.gpr["B"] = memory[cpu.sp.pop()]
            }
            "0x0402" -> { //"INCC"
                memory[cpu.sp.add()] = cpu.gpr["C"] ?: 0
                cpu.gpr["A"] = memory[cpu.sp.pop()]
                cpu.alu.setA(cpu.gpr["A"] ?: 0)
                cpu.alu.setB(1)
                cpu.alu.add()
                cpu.gpr["A"] = cpu.alu.getRes()
                memory[cpu.sp.add()] = cpu.gpr["A"] ?: 0
                cpu.gpr["C"] = memory[cpu.sp.pop()]
            }
            "0x0403" -> { //"INCD"
                memory[cpu.sp.add()] = cpu.gpr["D"] ?: 0
                cpu.gpr["A"] = memory[cpu.sp.pop()]
                cpu.alu.setA(cpu.gpr["A"] ?: 0)
                cpu.alu.setB(1)
                cpu.alu.add()
                cpu.gpr["A"] = cpu.alu.getRes()
                memory[cpu.sp.add()] = cpu.gpr["A"] ?: 0
                cpu.gpr["D"] = memory[cpu.sp.pop()]
            }
            "0x0404" -> { //"INCE"
                memory[cpu.sp.add()] = cpu.gpr["E"] ?: 0
                cpu.gpr["A"] = memory[cpu.sp.pop()]
                cpu.alu.setA(cpu.gpr["A"] ?: 0)
                cpu.alu.setB(1)
                cpu.alu.add()
                cpu.gpr["A"] = cpu.alu.getRes()
                memory[cpu.sp.add()] = cpu.gpr["A"] ?: 0
                cpu.gpr["E"] = memory[cpu.sp.pop()]
            }
            "0x0405" -> { //"INCF"
                memory[cpu.sp.add()] = cpu.gpr["F"] ?: 0
                cpu.gpr["A"] = memory[cpu.sp.pop()]
                cpu.alu.setA(cpu.gpr["A"] ?: 0)
                cpu.alu.setB(1)
                cpu.alu.add()
                cpu.gpr["A"] = cpu.alu.getRes()
                memory[cpu.sp.add()] = cpu.gpr["A"] ?: 0
                cpu.gpr["F"] = memory[cpu.sp.pop()]
            }

            "0x0500" -> {//"SETA"
                cpu.pc.next()
                cpu.gpr["A"] = memory[cpu.pc.value]
            }
            "0x0501" -> {//"SETA"
                cpu.pc.next()
                cpu.gpr["B"] = memory[cpu.pc.value]
            }
            "0x0502" ->{//"SETC"
                cpu.pc.next()
                cpu.gpr["C"] = memory[cpu.pc.value]
            }
            "0x0503" -> {//"SETD"
                cpu.pc.next()
                cpu.gpr["D"] = memory[cpu.pc.value]
            }
            "0x0504" -> {//"SETE"
                cpu.pc.next()
                cpu.gpr["E"] = memory[cpu.pc.value]
            }
            "0x0505" -> { // SETF
                cpu.pc.next()
                cpu.gpr["F"] = memory[cpu.pc.value]
            }
        }

    }

    private fun printState(command: Int) {
        println("${command.codeToCommand()}: ${command.toHexFormat()}")
        println(cpu.gpr)
        println()
        println(memory.toString())
        println(cpu.flags)
        println(cpu.sp)
        println(cpu.pc)
        println("----------------------------------------")
    }
}
