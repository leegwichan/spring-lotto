package lotto.domain

import jakarta.persistence.*

@Entity
class Lotto(

    @Column(nullable = false)
    @Convert(converter = IntListConverter::class)
    val numbers: List<Int>,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
) {

    init {
        require(numbers.size == 6) { "로또 번호는 6개 이어야 합니다" }
        require(numbers.distinct().size == numbers.size) { "로또 번호는 중복되어서는 안됩니다" }
    }

    @Converter
    private class IntListConverter : AttributeConverter<List<Int>, String> {
        override fun convertToDatabaseColumn(attribute: List<Int>?): String {
            return attribute?.joinToString(",") ?: ""
        }

        override fun convertToEntityAttribute(data: String?): List<Int> {
            return data?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
        }
    }
}
