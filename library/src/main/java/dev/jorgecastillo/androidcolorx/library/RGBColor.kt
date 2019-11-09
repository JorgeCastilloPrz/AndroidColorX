package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

data class RGBColor(
    val red: Int,
    val green: Int,
    val blue: Int
) {
    override fun toString(): String {
        return "r:$red / g:$green / b:$blue"
    }
}

fun @receiver:ColorInt Int.asRGB(): RGBColor =
    RGBColor(Color.red(this), Color.green(this), Color.blue(this))

@ColorInt
fun RGBColor.asColorInt(): Int = Color.rgb(red, green, blue)

fun RGBColor.asArgb(): ARGBColor = asColorInt().asArgb()

fun RGBColor.asCmyk(): CMYKColor = asColorInt().asCMYK()

fun RGBColor.asHex(): HEXColor = asColorInt().asHex()

fun RGBColor.asHsl(): HSLColor = asColorInt().asHSL()

/**
 * @param value amount to lighten in the range 0...1
 */
fun RGBColor.lighten(value: Float): RGBColor = this.asColorInt().lighten(value).asRGB()

/**
 * @param value amount to lighten in the range 0...100
 */
fun RGBColor.lighten(value: Int): RGBColor = this.asColorInt().lighten(value).asRGB()

/**
 * @param value amount to darken in the range 0...1
 */
fun RGBColor.darken(value: Float): RGBColor = this.asColorInt().darken(value).asRGB()

/**
 * @param value amount to darken in the range 0...100
 */
fun RGBColor.darken(value: Int): RGBColor = this.asColorInt().darken(value).asRGB()

/**
 * @return a list of shades for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a RGBColor.
 */
fun RGBColor.getShades(): List<RGBColor> = asColorInt().getShades().map { it.asRGB() }

/**
 * @return a list of tints for the given color like the ones in https://www.color-hex.com/color/e91e63.
 * Each one of the colors is a RGBColor.
 */
fun RGBColor.getTints(): List<RGBColor> = asColorInt().getTints().map { it.asRGB() }

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The complimentary color stands for the
 * color in the opposite side of the circle, so it's (hue + 180) % 360.
 */
fun RGBColor.complimentary(): RGBColor = asColorInt().complimentary().asRGB()

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The triadic colors stand for 3 colors that
 * compose a perfect triangle (equal sides) over the circle, so they are equally far from each other.
 *
 * Triadic colors for h0 would be (hue + 120) % 360 and (hue + 240) % 360.
 */
fun RGBColor.triadic(): Pair<RGBColor, RGBColor> =
    asColorInt().triadic().let { Pair(it.first.asRGB(), it.second.asRGB()) }

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The tetradic colors stand for 4 colors
 * that compose a perfect square (equal sides) over the circle, so they are equally far from each other.
 *
 * Tetradic colors for h0 would be (hue + 90) % 360, (hue + 180) % 360 and (hue + 270) % 360.
 */
fun RGBColor.tetradic(): Triple<RGBColor, RGBColor, RGBColor> =
    asColorInt().tetradic().let { Triple(it.first.asRGB(), it.second.asRGB(), it.third.asRGB()) }

/**
 * The Hue is the colour's position on the colour wheel, expressed in degrees from 0° to 359°, representing the 360° of
 * the wheel; 0° being red, 180° being red's opposite colour cyan, and so on. The analogous colors stand for 3 colors
 * that are together in the circle, separated by 30 degrees each.
 *
 * Analogous colors for h0 would be (hue + 30) % 360 & (hue - 30) % 360.
 */
fun RGBColor.analogous(): Pair<RGBColor, RGBColor> =
    asColorInt().analogous().let { Pair(it.first.asRGB(), it.second.asRGB()) }

/**
 * Check if a color is dark (convert to XYZ & check Y component)
 */
fun RGBColor.isDark(): Boolean = ColorUtils.calculateLuminance(this.asColorInt()) < 0.5
