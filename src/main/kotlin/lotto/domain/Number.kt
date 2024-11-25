package lotto.domain

data class Number (val value: Int) {

    init {
        require(value in MIN_VALUE..MAX_VALUE) { "각 숫자는 ${MIN_VALUE}와 $MAX_VALUE 사이 이어야 합니다" }
    }

    companion object {
        private const val MIN_VALUE = 1
        private const val MAX_VALUE = 45
    }
}
