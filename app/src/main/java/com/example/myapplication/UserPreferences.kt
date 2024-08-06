package com.example.myapplication

data class UserPreferences(
    val booleanPref: Boolean,
    val intPref: Int,
    val longPref: Long,
    val floatPref: Float,
    val stringPref: String,
    val stringSetPref: Set<String>,
) {
    override fun toString(): String {
        return "booleanPref = $booleanPref\n" +
                "intPref = $intPref\n" +
                "longPref = $longPref\n" +
                "floatPref = $floatPref\n" +
                "stringPref = $stringPref\n" +
                "stringSetPref = $stringSetPref\n"
    }

    companion object {

        fun empty(): UserPreferences = UserPreferences(
            booleanPref = false,
            intPref = 0,
            longPref = 0L,
            floatPref = 0f,
            stringPref = "",
            stringSetPref = emptySet()
        )
    }
}
