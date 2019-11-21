package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.core.graphics.ColorUtils

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

fun HSLColor.asRgb(): RGBColor = asColorInt().asRgb()

fun HSLColor.asArgb(): ARGBColor = asColorInt().asArgb()

fun HSLColor.asCmyk(): CMYKColor = asColorInt().asCmyk()

fun HSLColor.asHex(): HEXColor = asColorInt().asHex()

fun HSLColor.asHsla(): HSLAColor = asColorInt().asHsla()

/**
 * @param value amount to lighten in the range 0...1
 */
fun HSLColor.lighten(value: Float): HSLColor = this.asColorInt().lighten(value).asHsl()

/**
 * @param value amount to lighten in the range 0...100
 */
fun HSLColor.lighten(value: Int): HSLColor = this.asColorInt().lighten(value).asHsl()

/**
 * @param value amount to darken in the range 0...1
 */
fun HSLColor.darken(value: Float): HSLColor = this.asColorInt().darken(value).asHsl()

/**
 * @param value amount to darken in the range 0...100
 */
fun HSLColor.darken(value: Int): HSLColor = this.asColorInt().darken(value).asHsl()

/**
 * @return a list of shades for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a HSLColor.
 */
fun HSLColor.shades(): List<HSLColor> = asColorInt().shades().map { it.asHsl() }

/**
 * @return a list of tints for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a HSLColor.
 */
fun HSLColor.tints(): List<HSLColor> = asColorInt().tints().map { it.asHsl() }

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The complimentary color stands for the
 * color in the opposite side of the circle, so it's (hue + 180) % 360.
 */
fun HSLColor.complimentary(): HSLColor = asColorInt().complimentary().asHsl()

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The triadic colors stand for 3 colors that
 * compose a perfect triangle (equal sides) over the circle, so they are equally far from each other.
 *
 * Triadic colors for h0 would be (hue + 120) % 360 and (hue + 240) % 360.
 */
fun HSLColor.triadic(): Pair<HSLColor, HSLColor> =
    asColorInt().triadic().let { Pair(it.first.asHsl(), it.second.asHsl()) }

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The tetradic colors stand for 4 colors
 * that compose a perfect square (equal sides) over the circle, so they are equally far from each other.
 *
 * Tetradic colors for h0 would be (hue + 90) % 360, (hue + 180) % 360 and (hue + 270) % 360.
 */
fun HSLColor.tetradic(): Triple<HSLColor, HSLColor, HSLColor> =
    asColorInt().tetradic().let { Triple(it.first.asHsl(), it.second.asHsl(), it.third.asHsl()) }

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
