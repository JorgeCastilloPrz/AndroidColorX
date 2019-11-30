package dev.jorgecastillo.androidcolorx.library

internal fun constrain(amount: Float, low: Float, high: Float): Float {
    return if (amount < low) low else if (amount > high) high else amount
}
