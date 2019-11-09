package dev.jorgecastillo.androidcolorx.library

import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.ColorUtils.HSLToColor

/**
 * HSL stands for hue-saturation-lightness.
 *
 * Hue is a value from 0...360
 * Saturation is a value from 0...1
 * Lightness is a value from 0...1
 */
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

fun HSLColor.asFloatArray(): FloatArray = floatArrayOf(hue, saturation, lightness)

@ColorInt
fun HSLColor.asColorInt(): Int = HSLToColor(asFloatArray())

fun HSLColor.asRgb(): RGBColor = asColorInt().asRGB()

fun HSLColor.asArgb(): ARGBColor = asColorInt().asArgb()

fun HSLColor.asCmyk(): CMYKColor = asColorInt().asCMYK()

fun HSLColor.asHex(): HEXColor = asColorInt().asHex()

fun HSLColor.asHsl(): HSLColor = asColorInt().asHSL()

/**
 * @param value amount to lighten in the range 0...1
 */
fun HSLColor.lighten(value: Float): HSLColor = this.asColorInt().lighten(value).asHSL()

/**
 * @param value amount to lighten in the range 0...100
 */
fun HSLColor.lighten(value: Int): HSLColor = this.asColorInt().lighten(value).asHSL()

/**
 * @param value amount to darken in the range 0...1
 */
fun HSLColor.darken(value: Float): HSLColor = this.asColorInt().darken(value).asHSL()

/**
 * @param value amount to darken in the range 0...100
 */
fun HSLColor.darken(value: Int): HSLColor = this.asColorInt().darken(value).asHSL()

/**
 * @return a list of shades for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a HSLColor.
 */
fun HSLColor.getShades(): List<HSLColor> = asColorInt().getShades().map { it.asHSL() }

/**
 * @return a list of tints for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a HSLColor.
 */
fun HSLColor.getTints(): List<HSLColor> = asColorInt().getTints().map { it.asHSL() }
