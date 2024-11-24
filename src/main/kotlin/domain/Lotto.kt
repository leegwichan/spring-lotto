package domain

import jakarta.persistence.*

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
