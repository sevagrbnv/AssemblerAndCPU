import execute_components.CompilerImpl
import cpu_components.*

fun provideCPU(memory: Memory): CPU {
    return CPU(
        provideSP(memory.size),
        provideALU(),
        provideFlags(memory),
        providePC(),
        provideGPR()
    )
}

fun provideCompiler(path: String): execute_components.Compiler = CompilerImpl(path)

fun provideMemory() = Memory()

fun provideSP(size: Int) = SP(size)

fun provideALU() = ALU()

fun provideFlags(memory: Memory) = Flags(memory)

fun providePC() = PC()

fun provideGPR() = GPR()