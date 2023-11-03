package cpu_components

class PC {
    var value: Int = 0

    fun next() {
        value += 1
    }

    fun reset() {
        value = 0
    }

    override fun toString(): String {
        return "Current PC position: $value"
    }
}