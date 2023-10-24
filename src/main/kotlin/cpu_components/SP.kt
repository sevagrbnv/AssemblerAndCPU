package cpu_components

import exceptions.EmptyStackEx
import exceptions.StackOverflowEx

// stack pointer
class SP(private val size: Int) {

    private var value = size - 1

    fun seek() = value

    fun add(): Int {
        if (value == -1)
            throw StackOverflowEx()
        return value--
    }

    fun pop(): Int {
        if (value >= size)
            throw EmptyStackEx()
        return ++value
    }

    override fun toString(): String {
        return "Current SP value: $value"
    }
}