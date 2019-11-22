package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
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
fun @receiver:ColorInt Int.asCmyk(): CMYKColor {
    val r = Color.red(this)
    val g = Color.green(this)
    val b = Color.blue(this)

    val r1 = r / 255f
    val g1 = g / 255f
    val b1 = b / 255f

    val k = 1.0f - max(r1, max(g1, b1))

    val cyan = if (k == 1f) 0f else (1.0f - r1 - k) / (1.0f - k)
    val magenta = if (k == 1f) 0f else (1.0f - g1 - k) / (1.0f - k)
    val yellow = if (k == 1f) 0f else (1.0f - b1 - k) / (1.0f - k)

    return CMYKColor(cyan, magenta, yellow, k)
}

@ColorInt
fun CMYKColor.asColorInt(): Int {
    val red = (1 - cyan) * (1 - key)
    val green = (1 - magenta) * (1 - key)
    val blue = (1 - yellow) * (1 - key)

    return rgbColorInt(red, green, blue)
}

fun CMYKColor.asRgb(): RGBColor = asColorInt().asRgb()

fun CMYKColor.asArgb(): ARGBColor = asColorInt().asArgb()

fun CMYKColor.asHex(): HEXColor = asColorInt().asHex()

fun CMYKColor.asHsl(): HSLColor = asColorInt().asHsl()

fun CMYKColor.asHsla(): HSLAColor =
    asHsl().let { HSLAColor(it.hue, it.saturation, it.lightness, 1f) }

/**
 * @param value amount to lighten in the range 0...1
 */
fun CMYKColor.lighten(value: Float): CMYKColor = this.asColorInt().lighten(value).asCmyk()

/**
 * @param value amount to lighten in the range 0...100
 */
fun CMYKColor.lighten(value: Int): CMYKColor = this.asColorInt().lighten(value).asCmyk()

/**
 * @param value amount to darken in the range 0...1
 */
fun CMYKColor.darken(value: Float): CMYKColor = this.asColorInt().darken(value).asCmyk()

/**
 * @param value amount to darken in the range 0...100
 */
fun CMYKColor.darken(value: Int): CMYKColor = this.asColorInt().darken(value).asCmyk()

/**
 * @return a list of shades for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a CMYKColor.
 */
fun CMYKColor.shades(): List<CMYKColor> = asColorInt().shades().map { it.asCmyk() }

/**
 * @return a list of tints for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a CMYKColor.
 */
fun CMYKColor.tints(): List<CMYKColor> = asColorInt().tints().map { it.asCmyk() }

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The complimentary color stands for the
 * color in the opposite side of the circle, so it's (hue + 180) % 360.
 */
fun CMYKColor.complimentary(): CMYKColor = asColorInt().complimentary().asCmyk()

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The triadic colors stand for 3 colors that
 * compose a perfect triangle (equal sides) over the circle, so they are equally far from each other.
 *
 * Triadic colors for h0 would be (hue + 120) % 360 and (hue + 240) % 360.
 */
fun CMYKColor.triadic(): Pair<CMYKColor, CMYKColor> =
    asColorInt().triadic().let { Pair(it.first.asCmyk(), it.second.asCmyk()) }

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The tetradic colors stand for 4 colors
 * that compose a perfect square (equal sides) over the circle, so they are equally far from each other.
 *
 * Tetradic colors for h0 would be (hue + 90) % 360, (hue + 180) % 360 and (hue + 270) % 360.
 */
fun CMYKColor.tetradic(): Triple<CMYKColor, CMYKColor, CMYKColor> =
    asColorInt().tetradic().let { Triple(it.first.asCmyk(), it.second.asCmyk(), it.third.asCmyk()) }

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The analogous colors stand for 3 colors
 * that are together in the circle, separated by 30 degrees each.
 *
 * Analogous colors for h0 would be (hue + 30) % 360 & (hue - 30) % 360.
 */
fun CMYKColor.analogous(): Pair<CMYKColor, CMYKColor> =
    asColorInt().analogous().let { Pair(it.first.asCmyk(), it.second.asCmyk()) }

/**
 * Check if a color is dark (convert to XYZ & check Y component)
 */
fun CMYKColor.isDark(): Boolean = ColorUtils.calculateLuminance(this.asColorInt()) < 0.5

/**
 * Returns a color that contrasts nicely with the one given (receiver). Fallbacks to white and
 * black, but optional light and dark colors can be passed.
 */
fun CMYKColor.contrasting(
    lightColor: CMYKColor = CMYKColor(0f, 0f, 0f, 0f), // white
    darkColor: CMYKColor = CMYKColor(0f, 0f, 0f, 1f) // black
) = if (isDark()) {
    lightColor
} else {
    darkColor
}