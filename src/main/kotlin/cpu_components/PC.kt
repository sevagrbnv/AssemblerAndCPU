package cpu_components

class PC {
    var value: Int = 0

    fun seekNext() = value + 1

    fun next() {
        value += 1
    }

    override fun toString(): String {
        return "Current PC position: $value"
    }
}