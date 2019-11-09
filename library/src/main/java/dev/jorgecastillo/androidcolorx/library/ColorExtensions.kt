package dev.jorgecastillo.androidcolorx.library

import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

/**
 * Check if a color is dark (convert to XYZ & check Y component)
 */
fun @receiver:ColorInt Int.isDark(): Boolean = ColorUtils.calculateLuminance(this) < 0.5

@ColorInt
fun @receiver:ColorInt Int.lighten(value: Float): Int {
    val hsl = this.asHSL()
    var lightness = hsl.lightness
    lightness += value / 100
    lightness = 0f.coerceAtLeast(lightness.coerceAtMost(1f))
    return HSLColor(hsl.hue, hsl.saturation, lightness).asColorInt()
}

@ColorInt
fun @receiver:ColorInt Int.darken(value: Float): Int {
    val hsl = this.asHSL()
    var lightness = hsl.lightness
    lightness -= value / 100
    lightness = 0f.coerceAtLeast(lightness.coerceAtMost(1f))
    return HSLColor(hsl.hue, hsl.saturation, lightness).asColorInt()
}
