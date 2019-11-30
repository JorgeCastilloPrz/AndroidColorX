package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.core.graphics.ColorUtils
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

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
)

@NonNull
fun @receiver:ColorInt Int.asHsl(): HSLColor = this.let { color ->
    FloatArray(3).apply {
        rgbToHsl(Color.red(color), Color.green(color), Color.blue(color), this)
    }.let {
        HSLColor(it[0], it[1], it[2])
    }
}

@ColorInt
fun HSLColor.asColorInt(): Int = hslToColor(floatArrayOf(hue, saturation, lightness))

private fun HSLColor.asRGBF(): FloatArray {
    val hsl = floatArrayOf(hue, saturation, lightness)

    val h = hsl[0]
    val s = hsl[1]
    val l = hsl[2]

    val c = (1f - abs(2 * l - 1f)) * s
    val m = l - 0.5f * c
    val x = c * (1f - abs(h / 60f % 2f - 1f))

    val hueSegment = h.toInt() / 60

    var r = 0f
    var g = 0f
    var b = 0f

    when (hueSegment) {
        0 -> {
            r = (c + m)
            g = (x + m)
            b = m
        }
        1 -> {
            r = (x + m)
            g = (c + m)
            b = m
        }
        2 -> {
            r = m
            g = (c + m)
            b = (x + m)
        }
        3 -> {
            r = m
            g = (x + m)
            b = (c + m)
        }
        4 -> {
            r = (x + m)
            g = m
            b = (c + m)
        }
        5, 6 -> {
            r = (c + m)
            g = m
            b = (x + m)
        }
    }

    r = constrain(r, 0f, 1f)
    g = constrain(g, 0f, 1f)
    b = constrain(b, 0f, 1f)

    return floatArrayOf(r, g, b)
}

private fun FloatArray.asCmyk(): CMYKColor {
    val r1 = this[0]
    val g1 = this[1]
    val b1 = this[2]
    val k = 1.0f - max(r1, max(g1, b1))

    val cyan = if (k == 1f) 0f else (1.0f - r1 - k) / (1.0f - k)
    val magenta = if (k == 1f) 0f else (1.0f - g1 - k) / (1.0f - k)
    val yellow = if (k == 1f) 0f else (1.0f - b1 - k) / (1.0f - k)

    return CMYKColor(cyan, magenta, yellow, k)
}

fun HSLColor.asRgb(): RGBColor = asColorInt().asRgb()

fun HSLColor.asArgb(): ARGBColor = asRgb().asArgb()

fun HSLColor.asCmyk(): CMYKColor = asRGBF().asCmyk()

fun HSLColor.asHex(): HEXColor = asColorInt().asHex()

fun HSLColor.asHsla(): HSLAColor = HSLAColor(hue, saturation, lightness, 1f)

fun HSLColor.asHsv(): HSVColor = asColorInt().asHsv()

/**
 * @param value amount to lighten in the range 0...1
 */
fun HSLColor.lighten(value: Float): HSLColor {
    var mutableLightness = this.lightness
    mutableLightness += value
    mutableLightness = 0f.coerceAtLeast(mutableLightness.coerceAtMost(1f))
    return HSLColor(hue, saturation, mutableLightness)
}

/**
 * @param value amount to lighten in the range 0...100
 */
fun HSLColor.lighten(value: Int): HSLColor {
    var mutableLightness = this.lightness
    mutableLightness += value / 100f
    mutableLightness = 0f.coerceAtLeast(mutableLightness.coerceAtMost(1f))
    return HSLColor(hue, saturation, mutableLightness)
}

/**
 * @param value amount to darken in the range 0...1
 */
fun HSLColor.darken(value: Float): HSLColor {
    var mutableLightness = this.lightness
    mutableLightness -= value
    mutableLightness = 0f.coerceAtLeast(mutableLightness.coerceAtMost(1f))
    return HSLColor(hue, saturation, mutableLightness)
}

/**
 * @param value amount to darken in the range 0...100
 */
fun HSLColor.darken(value: Int): HSLColor {
    var mutableLightness = this.lightness
    mutableLightness -= value / 100f
    mutableLightness = 0f.coerceAtLeast(mutableLightness.coerceAtMost(1f))
    return HSLColor(hue, saturation, mutableLightness)
}

/**
 * @return a list of shades for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a HSLColor.
 *
 * @param count of shades to generate over the source color. It generates 10 by default.
 */
fun HSLColor.shades(count: Int = 10): List<HSLColor> {
    require(count > 0) { "count must be > 0" }
    val start = (this.lightness * 10000000).roundToInt()
    val step = if (start > 0) {
        -1 * start / count
    } else 1
    return IntProgression.fromClosedRange(start, 0, step).map { i ->
        this.copy(lightness = i / 10000000f)
    }
}

/**
 * @return a list of tints for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a HSLColor.
 *
 * @param count of tints to generate over the source color. It generates 10 by default.
 */
fun HSLColor.tints(count: Int = 10): List<HSLColor> {
    require(count > 0) { "count must be > 0" }

    val start = (this.lightness * 10000000).roundToInt()
    val step = if (start < 10000000) (10000000 - start) / count else 1
    return IntProgression.fromClosedRange(start, 10000000, step).map { i ->
        this.copy(lightness = i / 10000000f)
    }
}

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The complimentary color stands for the
 * color in the opposite side of the circle, so it's (hue + 180) % 360.
 */
fun HSLColor.complimentary(): HSLColor {
    val complimentaryHue = (hue + 180) % 360
    return this.copy(hue = complimentaryHue)
}

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°,
 * representing the 360° of the wheel; 0° being red, 180° being red's opposite colour cyan, and so
 * on. The triadic colors stand for 3 colors that compose a perfect triangle (equal sides) over the
 * circle, so they are equally far from each other.
 *
 * Triadic colors for h0 would be (hue + 120) % 360 and (hue + 240) % 360.
 */
fun HSLColor.triadic(): Pair<HSLColor, HSLColor> {
    val h1 = this.copy(hue = (this.hue + 120) % 360f)
    val h2 = this.copy(hue = (this.hue + 240) % 360f)

    return Pair(h1, h2)
}

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The tetradic colors stand for 4 colors
 * that compose a perfect square (equal sides) over the circle, so they are equally far from each other.
 *
 * Tetradic colors for h0 would be (hue + 90) % 360, (hue + 180) % 360 and (hue + 270) % 360.
 */
fun HSLColor.tetradic(): Triple<HSLColor, HSLColor, HSLColor> {
    val hue = this.hue // 0° to 359°

    val h1 = this.copy(hue = (hue + 90) % 360)
    val h2 = this.copy(hue = (hue + 180) % 360)
    val h3 = this.copy(hue = (hue + 270) % 360)

    return Triple(h1, h2, h3)
}

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The analogous colors stand for 3 colors
 * that are together in the circle, separated by 30 degrees each.
 *
 * Analogous colors for h0 would be (hue + 30) % 360 & (hue - 30) % 360.
 */
fun HSLColor.analogous(): Pair<HSLColor, HSLColor> =
    asColorInt().analogous().let { Pair(it.first.asHsl(), it.second.asHsl()) }

/**
 * Check if a color is dark (convert to XYZ & check Y component)
 */
fun HSLColor.isDark(): Boolean = ColorUtils.calculateLuminance(this.asColorInt()) < 0.5

/**
 * Returns a color that contrasts nicely with the one given (receiver). Fallbacks to white and
 * black, but optional light and dark colors can be passed.
 */
fun HSLColor.contrasting(
    lightColor: HSLColor = HSLColor(0f, 0f, 1f), // white
    darkColor: HSLColor = HSLColor(0f, 0f, 0f) // black
) = if (isDark()) {
    lightColor
} else {
    darkColor
}
