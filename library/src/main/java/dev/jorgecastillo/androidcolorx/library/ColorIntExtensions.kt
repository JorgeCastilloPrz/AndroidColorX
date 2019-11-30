package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.core.graphics.ColorUtils
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Return a color-int from red, green, blue float components
 * in the range \([0..1]\). The alpha component is implicitly
 * 1.0 (fully opaque). If the components are out of range, the
 * returned color is undefined.
 *
 * @param red Red component \([0..1]\) of the color
 * @param green Green component \([0..1]\) of the color
 * @param blue Blue component \([0..1]\) of the color
 */
@ColorInt
fun rgbColorInt(red: Float, green: Float, blue: Float): Int {
    return -0x1000000 or
            ((red * 255.0f + 0.5f).toInt() shl 16) or
            ((green * 255.0f + 0.5f).toInt() shl 8) or
            (blue * 255.0f + 0.5f).toInt()
}

/**
 * Convert HSL (hue-saturation-lightness) components to a RGB color.
 *
 *  * hsl[0] is Hue [0 .. 360)
 *  * hsl[1] is Saturation [0...1]
 *  * hsl[2] is Lightness [0...1]
 *
 * If hsv values are out of range, they are pinned.
 *
 * @param hsl 3-element array which holds the input HSL components
 * @return the resulting RGB color
 */
@ColorInt
fun hslToColor(hsl: FloatArray): Int {
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

    return rgbColorInt(r, g, b)
}

/**
 * Convert RGB components to HSL (hue-saturation-lightness).
 *
 *  * outHsl[0] is Hue [0 .. 360)
 *  * outHsl[1] is Saturation [0...1]
 *  * outHsl[2] is Lightness [0...1]
 *
 *
 * @param r red component value [0..255]
 * @param g green component value [0..255]
 * @param b blue component value [0..255]
 * @param outHsl 3-element array which holds the resulting HSL components
 */
fun rgbToHsl(
    @IntRange(from = 0x0, to = 0xFF) r: Int,
    @IntRange(from = 0x0, to = 0xFF) g: Int,
    @IntRange(from = 0x0, to = 0xFF) b: Int,
    outHsl: FloatArray
) {
    val rf = r / 255f
    val gf = g / 255f
    val bf = b / 255f

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

/**
 * Check if a color is dark (convert to XYZ & check Y component)
 */
fun @receiver:ColorInt Int.isDark(): Boolean = ColorUtils.calculateLuminance(this) < 0.5

/**
 * Returns a color that contrasts nicely with the one given (receiver). Fallbacks to white and
 * black, but optional light and dark colors can be passed.
 */
fun @receiver:ColorInt Int.contrasting(
    @ColorInt lightColor: Int = Color.WHITE,
    @ColorInt darkColor: Int = Color.BLACK
) = if (isDark()) {
    lightColor
} else {
    darkColor
}

/**
 * @param value amount to lighten in the range 0...1
 */
@ColorInt
fun @receiver:ColorInt Int.lighten(value: Float): Int {
    val hsla = this.asHsla()
    var lightness = hsla.lightness
    lightness += value
    lightness = 0f.coerceAtLeast(lightness.coerceAtMost(1f))
    return HSLAColor(hsla.hue, hsla.saturation, lightness, hsla.alpha).asColorInt()
}

/**
 * @param value amount to lighten in the range 0...100
 */
@ColorInt
fun @receiver:ColorInt Int.lighten(value: Int): Int {
    val hsla = this.asHsla()
    var lightness = hsla.lightness
    lightness += value / 100f
    lightness = 0f.coerceAtLeast(lightness.coerceAtMost(1f))
    return HSLAColor(hsla.hue, hsla.saturation, lightness, hsla.alpha).asColorInt()
}

/**
 * @param value amount to darken in the range 0...1
 */
@ColorInt
fun @receiver:ColorInt Int.darken(value: Float): Int {
    val hsla = this.asHsla()
    var lightness = hsla.lightness
    lightness -= value
    lightness = 0f.coerceAtLeast(lightness.coerceAtMost(1f))
    return HSLAColor(hsla.hue, hsla.saturation, lightness, hsla.alpha).asColorInt()
}

/**
 * @param value amount to darken in the range 0...100
 */
@ColorInt
fun @receiver:ColorInt Int.darken(value: Int): Int {
    val hsla = this.asHsla()
    var lightness = hsla.lightness
    lightness -= value / 100f
    lightness = 0f.coerceAtLeast(lightness.coerceAtMost(1f))
    return HSLAColor(hsla.hue, hsla.saturation, lightness, hsla.alpha).asColorInt()
}

/**
 * @return a list of shades for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a ColorInt.
 *
 * @param count of shades to generate over the source color. It generates 10 by default.
 */
fun @receiver:ColorInt Int.shades(count: Int = 10): List<Int> {
    require(count > 0) { "count must be > 0" }
    val hsla = this.asHsla()

    val start = (hsla.lightness * 10000000).roundToInt()
    val step = if (start > 0) {
        -1 * start / count
    } else 1
    return IntProgression.fromClosedRange(start, 0, step).map { i ->
        hsla.copy(lightness = i / 10000000f).asColorInt()
    }
}

/**
 * @return a list of tints for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a ColorInt.
 *
 * @param count of tints to generate over the source color. It generates 10 by default.
 */
fun @receiver:ColorInt Int.tints(count: Int = 10): List<Int> {
    require(count > 0) { "count must be > 0" }
    val hsla = this.asHsla()

    val start = (hsla.lightness * 10000000).roundToInt()
    val step = if (start < 10000000) (10000000 - start) / count else 1
    return IntProgression.fromClosedRange(start, 10000000, step).map { i ->
        hsla.copy(lightness = i / 10000000f).asColorInt()
    }
}

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The complimentary color stands for the
 * color in the opposite side of the circle, so it's (hue + 180) % 360.
 */
fun @receiver:ColorInt Int.complimentary(): Int {
    val colorHSLA = this.asHsla()

    val hue = colorHSLA.hue // 0° to 359°
    val complimentaryHue = (hue + 180) % 360
    return colorHSLA.copy(hue = complimentaryHue).asColorInt()
}

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The triadic colors stand for 3 colors that
 * compose a perfect triangle (equal sides) over the circle, so they are equally far from each other.
 *
 * Triadic colors for h0 would be (hue + 120) % 360 and (hue + 240) % 360.
 */
fun @receiver:ColorInt Int.triadic(): Pair<Int, Int> {
    val colorHSLA = this.asHsla()
    val hue = colorHSLA.hue // 0° to 359°

    val h1 = colorHSLA.copy(hue = (hue + 120) % 360)
    val h2 = colorHSLA.copy(hue = (hue + 240) % 360)

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
    val colorHSLA = this.asHsla()
    val hue = colorHSLA.hue // 0° to 359°

    val h1 = colorHSLA.copy(hue = (hue + 90) % 360)
    val h2 = colorHSLA.copy(hue = (hue + 180) % 360)
    val h3 = colorHSLA.copy(hue = (hue + 270) % 360)

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
    val colorHSLA = this.asHsla()
    val hue = colorHSLA.hue // 0° to 359°

    val h1 = colorHSLA.copy(hue = (hue + 30) % 360)
    val h2 = colorHSLA.copy(hue = (hue + 330) % 360)

    return Pair(h1.asColorInt(), h2.asColorInt())
}
