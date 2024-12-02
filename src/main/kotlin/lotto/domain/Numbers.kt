package lotto.domain

class Numbers(private val numbers: List<Number>) {
    val values: List<Int>
        get() = numbers.map { it.value }

    init {
        require(numbers.size == NUMBER_SIZE) { "로또 번호는 ${NUMBER_SIZE}개 이어야 합니다" }
        require(numbers.distinct().size == numbers.size) { "로또 번호는 중복되어서는 안됩니다" }
    }

    fun countMatchingNumbers(numbers: Numbers): Int = this.numbers.intersect(numbers.numbers).size

    fun isContained(number: Number): Boolean = this.numbers.contains(number)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Numbers
        return numbers == other.numbers
    }

    override fun hashCode(): Int {
        return numbers.hashCode()
    }

    companion object {
        private const val NUMBER_SIZE = 6

        fun from(integers: List<Int>): Numbers {
            val numbers = integers.map { Number(it) }
            return Numbers(numbers)
        }
    }
}
