package cpu_components

class Memory(override val size: Int = SIZE) : ArrayList<String>(size) {
    init {
        reset()
    }

    private fun reset() {
        repeat(SIZE) {
            this.add("0x0000")
        }
    }

    override fun toString(): String {
        val result = StringBuilder()
        this.chunked(CHUNK).forEach {
            result.appendLine(it.joinToString(" "))
        }
        return "Memory:\n${result}"
    }

    companion object {
        private const val SIZE = 64
        private const val CHUNK = 8
    }
}