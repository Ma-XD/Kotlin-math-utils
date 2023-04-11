package fraction

import kotlin.math.sign

@Suppress("unused")
data class Quotient(
    private var numerator: Int,
    private var denominator: Int
) : Number(), Comparable<Quotient> {

    companion object {
        private tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
        private fun lcm(a: Int, b: Int): Int = a * b / gcd(a, b)
    }

    init {
        require(den != 0) { "Division by 0" }
        reduce()
    }

    private fun reduce() {
        val gcd = gcd(numerator, denominator)
        numerator /= gcd
        denominator /= gcd
        numerator
        if (denominator < 0) {
            numerator = -numerator
            denominator = -denominator
        }
    }

    val num: Int
        get() = numerator

    val den: Int
        get() = denominator

    val isInt: Boolean
        get() = den == 1

    val intPart: Int
        get() = num / den

    val sign: Int
        get() = num.sign

    override fun compareTo(other: Quotient): Int = (this - other).sign

    operator fun plus(other: Quotient): Quotient {
        val lcm = lcm(den, other.den)
        return Quotient(
            numerator = num * (lcm / den) + other.num * (lcm / other.den),
            denominator = lcm
        )
    }

    operator fun minus(other: Quotient): Quotient {
        val lcm = lcm(den, other.den)
        return Quotient(
            numerator = num * (lcm / den) - other.num * (lcm / other.den),
            denominator = lcm
        )
    }

    operator fun times(other: Quotient): Quotient =
        Quotient(
            numerator = num * other.num,
            denominator = den * other.den
        )


    operator fun div(other: Quotient): Quotient =
        Quotient(
            numerator = num * other.den,
            denominator = den * other.num
        )

    operator fun unaryMinus(): Quotient = Quotient(-num, den)

    operator fun unaryPlus(): Quotient = Quotient(num, den)

    override fun toByte(): Byte = throw UnsupportedOperationException("Unsupported operation toByte() for Quotient")

    override fun toChar(): Char = throw UnsupportedOperationException("Unsupported operation toChar() for Quotient")

    override fun toDouble(): Double = num.toDouble() / den

    override fun toFloat(): Float = num.toFloat() / den

    override fun toInt(): Int = if (isInt) num else throw ArithmeticException("Quotient $this is not integer")

    override fun toLong(): Long = this.toInt().toLong()

    override fun toShort(): Short = this.toInt().toShort()

    override fun toString(): String = "$num/$den"
}