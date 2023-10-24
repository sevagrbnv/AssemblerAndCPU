package exceptions

class EmptyStackEx: IllegalStateException() {

    override fun toString(): String {
        return "Stack Pointer has incorrect value"
    }
}