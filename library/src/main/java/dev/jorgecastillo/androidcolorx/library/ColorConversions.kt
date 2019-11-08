package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.core.graphics.ColorUtils
import kotlin.math.max

fun @receiver:ColorInt Int.asRGB(): RGBColor =
    RGBColor(Color.red(this), Color.green(this), Color.blue(this))

fun @receiver:ColorInt Int.asARGB(): ARGBColor =
    ARGBColor(Color.alpha(this), Color.red(this), Color.green(this), Color.blue(this))

/**
 * Returns the color in complete hex format as in #FFFFFF.
 */
fun @receiver:ColorInt Int.asHex(): HEXColor = HEXColor(String.format("#%06X", 0xFFFFFF and this))

@NonNull
fun @receiver:ColorInt Int.asHSL(): HSLColor = this.let { color ->
    FloatArray(3).apply { ColorUtils.colorToHSL(color, this) }.let {
        HSLColor(it[0], it[1], it[2])
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
