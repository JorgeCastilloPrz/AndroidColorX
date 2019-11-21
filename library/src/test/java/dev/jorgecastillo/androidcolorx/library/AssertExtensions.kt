package dev.jorgecastillo.androidcolorx.library

import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import kotlin.math.round

infix fun <T> T.eqWithUnderstandablePrecisionLoss(other: T): Unit = when (this) {
    is CMYKColor -> {
        assertEquals(cyan.round(2), (other as CMYKColor).cyan.round(2))
        assertEquals(magenta.round(2), (other as CMYKColor).magenta.round(2))
        assertEquals(yellow.round(2), (other as CMYKColor).yellow.round(2))
        assertEquals(key.round(2), (other as CMYKColor).key.round(2))
    }
    is HSLColor -> {
        assertEquals(hue.round(2), (other as HSLColor).hue.round(2))
        assertEquals(saturation.round(2), (other as HSLColor).saturation.round(2))
        assertEquals(lightness.round(2), (other as HSLColor).lightness.round(2))
    }
    is HSLAColor -> {
        assertEquals(hue.round(2), (other as HSLAColor).hue.round(2))
        assertEquals(saturation.round(2), (other as HSLAColor).saturation.round(2))
        assertEquals(lightness.round(2), (other as HSLAColor).lightness.round(2))
        assertEquals(alpha.round(2), (other as HSLAColor).alpha.round(2))
    }
    else -> fail()
}

infix fun <T> Pair<T, T>.eqWithUnderstandablePrecisionLoss(other: Pair<T, T>): Unit {
    first eqWithUnderstandablePrecisionLoss other.first
    second eqWithUnderstandablePrecisionLoss other.second
}

infix fun <T> Triple<T, T, T>.eqWithUnderstandablePrecisionLoss(other: Triple<T, T, T>): Unit {
    first eqWithUnderstandablePrecisionLoss other.first
    second eqWithUnderstandablePrecisionLoss other.second
    third eqWithUnderstandablePrecisionLoss other.third
}

infix fun <T> List<T>.eqWithUnderstandablePrecisionLoss(other: List<T>): Unit =
    forEachIndexed { index, _ -> this[index] eqWithUnderstandablePrecisionLoss other[index] }

fun Float.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}
