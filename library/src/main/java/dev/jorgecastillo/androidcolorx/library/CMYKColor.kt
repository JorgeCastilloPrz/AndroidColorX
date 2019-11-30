package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.core.graphics.ColorUtils
import kotlin.math.max

data class CMYKColor(
    val cyan: Float,
    val magenta: Float,
    val yellow: Float,
    val key: Float
) {
    override fun toString(): String {
        return "$cyan / $magenta / $yellow / $key"
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

fun CMYKColor.asHsl(): HSLColor {
    val red = (1 - cyan) * (1 - key)
    val green = (1 - magenta) * (1 - key)
    val blue = (1 - yellow) * (1 - key)

    return FloatArray(3).apply {
        rgbFToHsl(red, green, blue, this)
    }.let {
        HSLColor(it[0], it[1], it[2])
    }
}

/**
 * Convert RGB components defined by floats [0..1] to HSL (hue-saturation-lightness).
 *
 *  * outHsl[0] is Hue [0 .. 360)
 *  * outHsl[1] is Saturation [0...1]
 *  * outHsl[2] is Lightness [0...1]
 *
 *
 * @param r red component value [0..1]
 * @param g green component value [0..1]
 * @param b blue component value [0..1]
 * @param outHsl 3-element array which holds the resulting HSL components
 */
fun rgbFToHsl(
    @FloatRange(from = 0.0, to = 1.0) rf: Float,
    @FloatRange(from = 0.0, to = 1.0) gf: Float,
    @FloatRange(from = 0.0, to = 1.0) bf: Float,
    outHsl: FloatArray
) {
    val max = Math.max(rf, Math.max(gf, bf))
    val min = Math.min(rf, Math.min(gf, bf))
    val deltaMaxMin = max - min

    var h: Float
    val s: Float
    val l = (max + min) / 2f

    if (max == min) {
        // Monochromatic
        s = 0f
        h = s
    } else {
        if (max == rf) {
            h = (gf - bf) / deltaMaxMin % 6f
        } else if (max == gf) {
            h = (bf - rf) / deltaMaxMin + 2f
        } else {
            h = (rf - gf) / deltaMaxMin + 4f
        }

        s = deltaMaxMin / (1f - Math.abs(2f * l - 1f))
    }

    h = h * 60f % 360f
    if (h < 0) {
        h += 360f
    }

    outHsl[0] = constrain(h, 0f, 360f)
    outHsl[1] = constrain(s, 0f, 1f)
    outHsl[2] = constrain(l, 0f, 1f)
}

fun CMYKColor.asHsla(): HSLAColor =
    asHsl().let { HSLAColor(it.hue, it.saturation, it.lightness, 1f) }

fun CMYKColor.asHsv(): HSVColor = asColorInt().asHsv()

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
 *
 * @param count of shades to generate over the source color. It generates 10 by default.
 */
fun CMYKColor.shades(count: Int = 10): List<CMYKColor> =
    asColorInt().shades(count).map { it.asCmyk() }

/**
 * @return a list of tints for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a CMYKColor.
 *
 * @param count of tints to generate over the source color. It generates 10 by default.
 */
fun CMYKColor.tints(count: Int = 10): List<CMYKColor> =
    asColorInt().tints(count).map { it.asCmyk() }

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
