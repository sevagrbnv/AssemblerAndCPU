package exceptions

class StackOverflowEx: IllegalStateException() {
    override fun toString(): String {
        return "Stack Overflow, negative value of Stack Pointer"
    }
}