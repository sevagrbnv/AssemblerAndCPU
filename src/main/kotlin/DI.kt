import execute_components.Compiler
import cpu_components.*
import execute_components.Executor

fun provideCPU(memory: Memory): CPU = CPU(
    provideSP(memory),
    provideALU(),
    provideFlags(memory),
    providePC(),
    provideGPR()
)

fun provideExecutor(): Executor = Executor()

fun provideCompiler() = Compiler()

fun provideMemory(): Memory = Memory()

fun provideSP(memory: Memory) = SP(memory)

fun provideALU(): ALU = ALU()

fun provideFlags(memory: Memory) = Flags(memory)

fun providePC() = PC()

fun provideGPR() = GPR()