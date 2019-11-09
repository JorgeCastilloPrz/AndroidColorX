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

/**
 * @param value amount to lighten in the range 0...1
 */
fun HEXColor.lighten(value: Float): HEXColor = this.asColorInt().lighten(value).asHex()

/**
 * @param value amount to lighten in the range 0...100
 */
fun HEXColor.lighten(value: Int): HEXColor = this.asColorInt().lighten(value).asHex()

/**
 * @param value amount to darken in the range 0...1
 */
fun HEXColor.darken(value: Float): HEXColor = this.asColorInt().darken(value).asHex()

/**
 * @param value amount to darken in the range 0...100
 */
fun HEXColor.darken(value: Int): HEXColor = this.asColorInt().darken(value).asHex()

/**
 * @return a list of shades for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a HEXColor.
 */
fun HEXColor.getShades(): List<HEXColor> = asColorInt().getShades().map { it.asHex() }

/**
 * @return a list of tints for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a HEXColor.
 */
fun HEXColor.getTints(): List<HEXColor> = asColorInt().getTints().map { it.asHex() }
