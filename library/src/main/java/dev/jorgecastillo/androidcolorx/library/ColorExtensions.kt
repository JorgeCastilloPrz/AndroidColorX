package dev.jorgecastillo.androidcolorx.library

import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import kotlin.math.roundToInt

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
    lightness += value / 100f
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
    lightness -= value / 100f
    lightness = 0f.coerceAtLeast(lightness.coerceAtMost(1f))
    return HSLColor(hsl.hue, hsl.saturation, lightness).asColorInt()
}

/**
 * @return a list of shades for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a ColorInt.
 */
fun @receiver:ColorInt Int.getShades(): List<Int> {
    val colorHSL = this.asHSL()

    val start = (colorHSL.lightness * 10000000).roundToInt()
    val step = if (start > 0) {
        -1 * start / 10
    } else 1
    return IntProgression.fromClosedRange(start, 0, step).map { i ->
        colorHSL.copy(lightness = i / 10000000f).asColorInt()
    }
}

/**
 * @return a list of tints for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a ColorInt.
 */
fun @receiver:ColorInt Int.getTints(): List<Int> {
    val colorHSL = this.asHSL()

    val start = (colorHSL.lightness * 10000000).roundToInt()
    val step = if (start < 10000000) (10000000 - start) / 10 else 1
    return IntProgression.fromClosedRange(start, 10000000, step).map { i ->
        colorHSL.copy(lightness = i / 10000000f).asColorInt()
    }
}

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The complimentary color stands for the
 * color in the opposite side of the circle, so it's (hue + 180) % 360.
 */
fun @receiver:ColorInt Int.complimentary(): Int {
    val colorHSL = this.asHSL()

    val hue = colorHSL.hue // 0° to 359°
    val complimentaryHue = (hue + 180) % 360
    return colorHSL.copy(hue = complimentaryHue).asColorInt()
}

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The triadic colors stand for 3 colors that
 * compose a perfect triangle (equal sides) over the circle, so they are equally far from each other.
 *
 * Triadic colors for h0 would be (hue + 120) % 360 and (hue + 240) % 360.
 */
fun @receiver:ColorInt Int.triadic(): Pair<Int, Int> {
    val colorHSL = this.asHSL()
    val hue = colorHSL.hue // 0° to 359°

    val h1 = colorHSL.copy(hue = (hue + 120) % 360)
    val h2 = colorHSL.copy(hue = (hue + 240) % 360)

    return Pair(h1.asColorInt(), h2.asColorInt())
}

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The tetradic colors stand for 4 colors
 * that compose a perfect square (equal sides) over the circle, so they are equally far from each other.
 *
 * Tetradic colors for h0 would be (hue + 90) % 360, (hue + 180) % 360 and (hue + 270) % 360.
 */
fun @receiver:ColorInt Int.tetradic(): Triple<Int, Int, Int> {
    val colorHSL = this.asHSL()
    val hue = colorHSL.hue // 0° to 359°

    val h1 = colorHSL.copy(hue = (hue + 90) % 360)
    val h2 = colorHSL.copy(hue = (hue + 180) % 360)
    val h3 = colorHSL.copy(hue = (hue + 270) % 360)

    return Triple(h1.asColorInt(), h2.asColorInt(), h3.asColorInt())
}

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The analogous colors stand for 3 colors
 * that are together in the circle, separated by 30 degrees each.
 *
 * Analogous colors for h0 would be (hue + 30) % 360 & (hue - 30) % 360.
 */
fun @receiver:ColorInt Int.analogous(): Pair<Int, Int> {
    val colorHSL = this.asHSL()
    val hue = colorHSL.hue // 0° to 359°

    val h1 = colorHSL.copy(hue = (hue + 30) % 360)
    val h2 = colorHSL.copy(hue = (hue + 330) % 360)

    return Pair(h1.asColorInt(), h2.asColorInt())
}
