package cpu_components


class CPU(
    val sp: SP,
    val alu: ALU,
    val flags: Flags,
    val pc: PC,
    val gpr: GPR
)