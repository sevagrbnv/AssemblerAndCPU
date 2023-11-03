package cpu_components

class ALU {
    private var a: Int = 0
    private var b: Int = 0
    private var res: Int = 0

    fun setA(value: Int) {
        a = value
    }

    fun setB(value: Int) {
        b = value
    }

    fun getRes() = res

    fun calc() {
        res = a + b
    }

    fun reset() {
        a = 0
        b = 0
        res = 0
    }
}