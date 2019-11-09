package dev.jorgecastillo.androidcolorx.library

import androidx.annotation.ColorInt

data class HEXColor(val hex: String) {
    val pureValue = hex.drop(1)

    override fun toString(): String {
        return hex
    }
}

/**
 * Returns the color in complete hex format as in #FFFFFF.
 */
fun @receiver:ColorInt Int.asHex(): HEXColor = HEXColor(String.format("#%06X", 0xFFFFFF and this))
