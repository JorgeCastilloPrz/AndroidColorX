package dev.jorgecastillo.androidcolorx.library

data class HSLColor(
    val hue: Float,
    val saturation: Float,
    val lightness: Float
) {
    override fun toString(): String {
        return "${String.format("%.2f", hue)}º / " +
                "${String.format("%.2f", saturation)} / " +
                String.format("%.2f", lightness)
    }
}
