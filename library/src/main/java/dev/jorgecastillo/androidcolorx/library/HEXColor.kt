package dev.jorgecastillo.androidcolorx.library

data class HEXColor(val hex: String) {
    val pureValue = hex.drop(1)

    override fun toString(): String {
        return hex
    }
}
