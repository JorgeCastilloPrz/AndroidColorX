package dev.jorgecastillo.androidcolorx.library

import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

/**
 * Check if a color is dark (convert to XYZ & check Y component)
 */
fun @receiver:ColorInt Int.isDark(): Boolean = ColorUtils.calculateLuminance(this) < 0.5
