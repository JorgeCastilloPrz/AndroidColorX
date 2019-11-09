package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
import androidx.annotation.ColorInt

data class RGBColor(
    val red: Int,
    val green: Int,
    val blue: Int
) {
    override fun toString(): String {
        return "$red / $green / $blue"
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
