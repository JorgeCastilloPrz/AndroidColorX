package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
import androidx.annotation.ColorInt

data class ARGBColor(
    val alpha: Int,
    val red: Int,
    val green: Int,
    val blue: Int
) {
    override fun toString(): String {
        return "$alpha / $red / $green / $blue"
    }
}

fun @receiver:ColorInt Int.asArgb(): ARGBColor =
    ARGBColor(Color.alpha(this), Color.red(this), Color.green(this), Color.blue(this))

@ColorInt
fun ARGBColor.asColorInt(): Int = Color.argb(alpha, red, green, blue)

fun ARGBColor.asRgb(): RGBColor = asColorInt().asRGB()

fun ARGBColor.asCmyk(): CMYKColor = asColorInt().asCMYK()

fun ARGBColor.asHex(): HEXColor = asColorInt().asHex()

fun ARGBColor.asHsl(): HSLColor = asColorInt().asHSL()

/**
 * @param value amount to lighten in the range 0...1
 */
fun ARGBColor.lighten(value: Float): ARGBColor = this.asColorInt().lighten(value).asArgb()

/**
 * @param value amount to lighten in the range 0...100
 */
fun ARGBColor.lighten(value: Int): ARGBColor = this.asColorInt().lighten(value).asArgb()

/**
 * @param value amount to darken in the range 0...1
 */
fun ARGBColor.darken(value: Float): ARGBColor = this.asColorInt().darken(value).asArgb()

/**
 * @param value amount to darken in the range 0...100
 */
fun ARGBColor.darken(value: Int): ARGBColor = this.asColorInt().darken(value).asArgb()
