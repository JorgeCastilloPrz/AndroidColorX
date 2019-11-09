package dev.jorgecastillo.androidcolorx.library

import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.core.graphics.ColorUtils

data class HSLColor(
    val hue: Float,
    val saturation: Float,
    val lightness: Float
) {
    override fun toString(): String {
        return "${String.format("%.2f", hue)}º / " +
                "${String.format("%.2f", saturation)} / " +
                String.format("%.2f", lightness)
    }
}

@NonNull
fun @receiver:ColorInt Int.asHSL(): HSLColor = this.let { color ->
    FloatArray(3).apply { ColorUtils.colorToHSL(color, this) }.let {
        HSLColor(it[0], it[1], it[2])
    }
}
