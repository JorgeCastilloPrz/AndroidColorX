package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 * Constructor:
 *
 * Parses the color string, and return the corresponding color-int. If the string cannot be parsed,
 * throws an IllegalArgumentException exception. Supported formats are:
 * #RRGGBB
 * #AARRGGBB
 * The following names are also accepted: red, blue, green, black, white, gray, cyan, magenta,
 * yellow, lightgray, darkgray, grey, lightgrey, darkgrey, aqua, fuchsia, lime, maroon, navy, olive,
 * purple, silver, and teal
 */
data class HEXColor(val hex: String) {
    init {
        // This is to enforce proper parsing. If the hex code does not match the Color.parseColor
        // format requirements we want it to throw.
        Color.parseColor(hex)
    }

    override fun toString(): String {
        return hex
    }
}

/**
 * Returns the color in complete hex format as in #FFFFFF.
 */
fun @receiver:ColorInt Int.asHex(): HEXColor = HEXColor(String.format("#%06X", 0xFFFFFF and this))

@ColorInt
fun HEXColor.asColorInt(): Int = Color.parseColor(hex)

fun HEXColor.asRgb(): RGBColor = asColorInt().asRGB()

fun HEXColor.asArgb(): ARGBColor = asColorInt().asArgb()

fun HEXColor.asCmyk(): CMYKColor = asColorInt().asCMYK()

fun HEXColor.asHex(): HEXColor = asColorInt().asHex()

fun HEXColor.asHsl(): HSLColor = asColorInt().asHSL()
