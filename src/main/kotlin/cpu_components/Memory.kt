package cpu_components

import utils.toHexFormat

class Memory(override val size: Int = MEMORY_SIZE) : ArrayList<Int>(size){
    init {
        reset()
    }

   fun reset() {
        repeat(MEMORY_SIZE) {
            this.add(0)
        }
    }

    override fun toString(): String {
        val result = StringBuilder()
        this.chunked(CHUNK_SIZE).forEach { chunk ->
            result.appendLine(chunk.joinToString(" ") { item ->
                item.toHexFormat()
            })
        }
        return "Memory:\n${result}"
    }

    companion object {
        private const val MEMORY_SIZE = 64
        private const val CHUNK_SIZE = 8
    }
}