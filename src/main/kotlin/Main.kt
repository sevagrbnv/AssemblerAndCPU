import java.nio.file.FileSystems
import java.nio.file.Path

// 84
// 101 0100
// безадресные команды
// фон неймана
// сумма элементов

fun main() {
    val sourceCode = "${getRootDirectory()}\\src\\main\\kotlin\\program"
    val compiler = provideCompiler()
    val compiledProgram = compiler.run(sourceCode)

    provideExecutor().also {
        it.run(compiledProgram)
    }
}

fun getRootDirectory(): Path = FileSystems.getDefault().getPath("").toAbsolutePath()