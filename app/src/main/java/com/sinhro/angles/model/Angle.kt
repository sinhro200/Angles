package com.sinhro.angles.model

data class Angle(
    val degree: Int,
    val minute: Int,
    val second: Int,
) {
    companion object {

        fun fromFloat(degree: Float): Angle {
            val deg = degree.toInt()
            val minFloat: Float = (degree - deg) * 60
            val min = minFloat.toInt()
            val secFloat = (minFloat - min) * 60
            val sec = secFloat.toInt()
            return Angle(
                deg,
                min,
                sec
            )
        }
    }
}