package lotto.domain

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Converter
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Lotto(
    @Column(nullable = false)
    @Convert(converter = NumbersConverter::class)
    val numbers: Numbers,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
) {
    constructor(numbers: List<Int>) : this(Numbers.from(numbers))

    @Converter
    private class NumbersConverter : AttributeConverter<Numbers, String> {
        override fun convertToDatabaseColumn(numbers: Numbers?): String {
            return numbers?.values?.joinToString(",") ?: ""
        }

        override fun convertToEntityAttribute(data: String?): Numbers {
            val values = data?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
            return Numbers.from(values)
        }
    }
}
