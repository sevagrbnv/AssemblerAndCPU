package cpu_components

import exceptions.EmptyStackEx
import exceptions.StackOverflowEx

// stack pointer
class SP(private val memory: Memory){

    private var value = memory.size - 1

    fun seek() = value

    fun add(): Int {
        if (value == -1)
            throw StackOverflowEx()
        return value--
    }

    fun pop(): Int {
        if (value >= memory.size - 1)
            throw EmptyStackEx()
        return ++value
    }

    fun reset() {
        value = memory.size
    }

    override fun toString(): String {
        return "Current SP value: $value"
    }
}