package exceptions

class CompilationError(private val command: String): IllegalStateException() {
    override fun toString(): String {
        return "Compilation error. Incorrect value: $command"
    }
}