import execute_components.Executor

// 84
// 101 0100
// безадресные команды
// фон неймана
// сумма элементов

fun main() {
    Executor().also {
        it.run()
    }
}

fun String.hexToInt() = this.substring(2).toInt(radix = 16)