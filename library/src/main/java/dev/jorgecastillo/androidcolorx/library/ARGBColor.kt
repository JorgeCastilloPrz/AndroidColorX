package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
import androidx.annotation.ColorInt

data class ARGBColor(
    val alpha: Int,
    val red: Int,
    val green: Int,
    val blue: Int
) {
    override fun toString(): String {
        return "$alpha / $red / $green / $blue"
    }
}

fun @receiver:ColorInt Int.asARGB(): ARGBColor =
    ARGBColor(Color.alpha(this), Color.red(this), Color.green(this), Color.blue(this))
