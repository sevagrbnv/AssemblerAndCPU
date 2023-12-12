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

    fun refresh(alu: ALU, sp: SP) {
        this["PF"] = alu.getRes().rem(2) == 0
        this["ZF"] = alu.getRes() == 0
        this["SF"] = alu.getRes() < 0
        this["0F"] = alu.getRes() > 32767
        this["SPE"] = sp.seek() == memory.size - 1
    }

    fun reset() {
        this["PF"] = false // Четность
        this["ZF"] = true // Нуль
        this["SF"] = false // Знак, по умолчанию "+"
        this["OF"] = false // Переполнение
        this["SPE"] = true // stack is empty
    }
}