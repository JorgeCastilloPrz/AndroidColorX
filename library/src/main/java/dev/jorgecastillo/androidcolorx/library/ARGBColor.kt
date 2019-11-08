package dev.jorgecastillo.androidcolorx.library

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
