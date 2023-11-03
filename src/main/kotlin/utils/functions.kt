package utils

fun String.hexToInt() = this.substring(2).toInt(radix = 16)

fun Int.toHexFormat() = String.format("0x%04X", this)

fun String.hexToCommand() = when (this) {
    "0x0000" -> "PUSH"
    "0x0001" -> "POP"
    "0x0002" -> "ADD"
    "0x0003" -> "SPE"
    "0x0004" -> "SNPE"
    "0x0006" -> "ACC"
    "0x000A" -> "HLT"
    "0x000B" -> "RST"
    else -> {this}
}