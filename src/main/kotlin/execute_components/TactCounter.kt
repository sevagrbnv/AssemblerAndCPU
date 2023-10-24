package execute_components

class TactCounter {
    private var value = 0

    fun next(): Int {
        value++
        if (value == 2)
            value = 0
        return value
    }

    fun get() = value
}