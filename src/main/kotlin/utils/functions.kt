package utils

import exceptions.CompilationError

fun String.hexToInt() = this.substring(2).toInt(radix = 16)

fun String.commandToHex(): Int {
    val command = when (this) {
        "NOPE" -> "0x0000"
        "HLT" -> "0xFFFF"

        "ADD" -> "0x0001"
        "CMP" -> "0x0002"

        "RMEM" -> "0x0F00"

        "J" -> "0x0010"
        "JNNF" -> "0x0011"
        "JNZ" -> "0x0012"

        "RSTA" -> "0x0100"
        "RSTB" -> "0x0101"
        "RSTC" -> "0x0102"
        "RSTD" -> "0x0103"
        "RSTE" -> "0x0104"
        "RSTF" -> "0x0105"



        "PUSHA" -> "0x0200"
        "PUSHB" -> "0x0201"
        "PUSHC" -> "0x0202"
        "PUSHD" -> "0x0203"
        "PUSHE" -> "0x0204"
        "PUSHF" -> "0x0205"

        "POPA" -> "0x0300"
        "POPB" -> "0x0301"
        "POPC" -> "0x0302"
        "POPD" -> "0x0303"
        "POPE" -> "0x0304"
        "POPF" -> "0x0305"

        "INCA" -> "0x0400"
        "INCB" -> "0x0401"
        "INCC" -> "0x0402"
        "INCD" -> "0x0403"
        "INCE" -> "0x0404"
        "INCF" -> "0x0405"

        "SETA" -> "0x0500"
        "SETB" -> "0x0501"
        "SETC" -> "0x0502"
        "SETD" -> "0x0503"
        "SETE" -> "0x0504"
        "SETF" -> "0x0505"

        else -> this
    }
    return if (command.contains("0x")) command.hexToInt()
    else try {
        command.toInt()
    } catch (e: java.lang.NumberFormatException) {
        throw CompilationError(command)
    }
}

fun Int.toHexFormat() = String.format("0x%04X", this)

fun Int.codeToCommand() = when (this.toHexFormat()) {
    "0x0000" -> "NOPE"
    "0xFFFF" -> "HLT"

    "0x0001" -> "ADD"
    "0x0002" -> "CMP"

    "0x0F00" -> "RMEM"

    "0x0010" -> "J"
    "0x0011" -> "JNNF"
    "0x0012" -> "JNZ"

    "0x0100" -> "RSTA"
    "0x0101" -> "RSTB"
    "0x0102" -> "RSTC"
    "0x0103" -> "RSTD"
    "0x0104" -> "RSTE"
    "0x0105" -> "RSTF"

    "0x0200" -> "PUSHA"
    "0x0201" -> "PUSHB"
    "0x0202" -> "PUSHC"
    "0x0203" -> "PUSHD"
    "0x0204" -> "PUSHE"
    "0x0205" -> "PUSHF"

    "0x0300" -> "POPA"
    "0x0301" -> "POPB"
    "0x0302" -> "POPC"
    "0x0303" -> "POPD"
    "0x0304" -> "POPE"
    "0x0305" -> "POPF"

    "0x0400" -> "INCA"
    "0x0401" -> "INCB"
    "0x0402" -> "INCC"
    "0x0403" -> "INCD"
    "0x0404" -> "INCE"
    "0x0405" -> "INCF"

    "0x0500" -> "SETA"
    "0x0501" -> "SETB"
    "0x0502" -> "SETC"
    "0x0503" -> "SETD"
    "0x0504" -> "SETE"
    "0x0505" -> "SETF"
    else -> {
        this
    }
}