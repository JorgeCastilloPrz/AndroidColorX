package dev.jorgecastillo.androidcolorx.library

data class CMYKColor(
    val cyan: Float,
    val magenta: Float,
    val yellow: Float,
    val key: Float
) {
    override fun toString(): String {
        return "${String.format("%.2f", cyan)} / " +
                "${String.format("%.2f", magenta)} / " +
                "${String.format("%.2f", yellow)} / " +
                String.format("%.2f", key)
    }
}
