package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
import androidx.annotation.ColorInt
import kotlin.math.max

data class CMYKColor(
    val cyan: Float,
    val magenta: Float,
    val yellow: Float,
    val key: Float
) {
    override fun toString(): String {
        return "${String.format("%.2f", cyan)} / " +
                "${String.format("%.2f", magenta)} / " +
                "${String.format("%.2f", yellow)} / " +
                String.format("%.2f", key)
    }
}

/**
 * Formula extracted from {@see https://www.rapidtables.com/convert/color/rgb-to-cmyk.html}.
 */
fun @receiver:ColorInt Int.asCMYK(): CMYKColor {
    val r = Color.red(this)
    val g = Color.green(this)
    val b = Color.blue(this)

    val r1 = r / 255f
    val g1 = g / 255f
    val b1 = b / 255f

    val k = 1.0f - max(r1, max(g1, b1))

    val cyan = (1.0f - r1 - k) / (1.0f - k)
    val magenta = (1.0f - g1 - k) / (1.0f - k)
    val yellow = (1.0f - b1 - k) / (1.0f - k)

    return CMYKColor(cyan, magenta, yellow, k)
}
