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

@ColorInt
fun CMYKColor.asColorInt(): Int {
    val red = 255 * (1 - cyan) * (1 - key)
    val green = 255 * (1 - magenta) * (1 - key)
    val blue = 255 * (1 - yellow) * (1 - key)

    return Color.rgb(red.toInt(), green.toInt(), blue.toInt())
}

fun CMYKColor.asRgb(): RGBColor = asColorInt().asRGB()

fun CMYKColor.asArgb(): ARGBColor = asColorInt().asArgb()

fun CMYKColor.asCmyk(): CMYKColor = asColorInt().asCMYK()

fun CMYKColor.asHex(): HEXColor = asColorInt().asHex()

fun CMYKColor.asHsl(): HSLColor = asColorInt().asHSL()

/**
 * @param value amount to lighten in the range 0...1
 */
fun CMYKColor.lighten(value: Float): CMYKColor = this.asColorInt().lighten(value).asCMYK()

/**
 * @param value amount to lighten in the range 0...100
 */
fun CMYKColor.lighten(value: Int): CMYKColor = this.asColorInt().lighten(value).asCMYK()

/**
 * @param value amount to darken in the range 0...1
 */
fun CMYKColor.darken(value: Float): CMYKColor = this.asColorInt().darken(value).asCMYK()

/**
 * @param value amount to darken in the range 0...100
 */
fun CMYKColor.darken(value: Int): CMYKColor = this.asColorInt().darken(value).asCMYK()
