package cpu_components

class Flags(private val memory: Memory) : HashMap<String, Boolean>() {

    // PF - Четность
    // ZF - Нуль
    // SF - Знак, по умолчанию "+"
    // OF - Переполнение
    // SPE - Стек пуст

    init {
        reset()
    }

    fun refresh(gpr: GPR, sp: SP) {
        this["PF"] = gpr["A"]?.rem(2) == 0
        this["ZF"] = gpr["A"] == 0
        this["SF"] = gpr["A"]!! < 0
        this["0F"] = gpr["A"]!! > 32767
        this["JSPE"] = sp.seek() == memory.size - 1
    }

    fun reset() {
        this["PF"] = false // Четность
        this["ZF"] = true // Нуль
        this["SF"] = false // Знак, по умолчанию "+"
        this["OF"] = false // Переполнение
        this["JSPE"] = true // stack is empty
    }
}