package cpu_components

class Memory(override val size: Int = MEMORY_SIZE) : ArrayList<String>(size) {
    init {
        reset()
    }

    private fun reset() {
        repeat(MEMORY_SIZE) {
            this.add("0x0000")
        }
    }

    override fun toString(): String {
        val result = StringBuilder()
        this.chunked(CHUNK_SIZE).forEach {
            result.appendLine(it.joinToString(" "))
        }
        return "Memory:\n${result}"
    }

    companion object {
        private const val MEMORY_SIZE = 64
        private const val CHUNK_SIZE = 8
    }
}