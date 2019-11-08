package dev.jorgecastillo.androidcolorx.library

data class RGBColor(
    val red: Int,
    val green: Int,
    val blue: Int
) {
    override fun toString(): String {
        return "$red / $green / $blue"
    }
}
