package dev.jorgecastillo.androidcolorx.library

import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

/**
 * Check if a color is dark (convert to XYZ & check Y component)
 */
fun @receiver:ColorInt Int.isDark(): Boolean = ColorUtils.calculateLuminance(this) < 0.5

/**
 * @param value amount to lighten in the range 0...1
 */
@ColorInt
fun @receiver:ColorInt Int.lighten(value: Float): Int {
    val hsl = this.asHSL()
    var lightness = hsl.lightness
    lightness += value
    lightness = 0f.coerceAtLeast(lightness.coerceAtMost(1f))
    return HSLColor(hsl.hue, hsl.saturation, lightness).asColorInt()
}

/**
 * @param value amount to lighten in the range 0...100
 */
@ColorInt
fun @receiver:ColorInt Int.lighten(value: Int): Int {
    val hsl = this.asHSL()
    var lightness = hsl.lightness
    lightness += value / 100
    lightness = 0f.coerceAtLeast(lightness.coerceAtMost(1f))
    return HSLColor(hsl.hue, hsl.saturation, lightness).asColorInt()
}

/**
 * @param value amount to darken in the range 0...1
 */
@ColorInt
fun @receiver:ColorInt Int.darken(value: Float): Int {
    val hsl = this.asHSL()
    var lightness = hsl.lightness
    lightness -= value
    lightness = 0f.coerceAtLeast(lightness.coerceAtMost(1f))
    return HSLColor(hsl.hue, hsl.saturation, lightness).asColorInt()
}

/**
 * @param value amount to darken in the range 0...100
 */
@ColorInt
fun @receiver:ColorInt Int.darken(value: Int): Int {
    val hsl = this.asHSL()
    var lightness = hsl.lightness
    lightness -= value / 100
    lightness = 0f.coerceAtLeast(lightness.coerceAtMost(1f))
    return HSLColor(hsl.hue, hsl.saturation, lightness).asColorInt()
}
