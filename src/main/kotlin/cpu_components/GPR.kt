package cpu_components// Регистры общего назначения

class GPR : HashMap<String, Int>() {
    init {
        reset()
    }

    fun reset() {
        this["A"] = 0
        this["B"] = 0
        this["C"] = 0
        this["D"] = 0
        this["E"] = 0
        this["F"] = 0
    }

    override fun toString(): String {
        return "General Purpose Register:\n${super<java.util.HashMap>.toString()}"
    }
}
