package dev.jorgecastillo.androidcolorx.library

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Assert.fail
import kotlin.math.round

infix fun <T> T.eqWithUnderstandablePrecisionLoss(other: T): Unit = when (this) {
    is CMYKColor -> {
        assertThat(this).all {
            assertThat(cyan.round(2)).isEqualTo((other as CMYKColor).cyan.round(2))
            assertThat(magenta.round(2)).isEqualTo((other as CMYKColor).magenta.round(2))
            assertThat(yellow.round(2)).isEqualTo((other as CMYKColor).yellow.round(2))
            assertThat(key.round(2)).isEqualTo((other as CMYKColor).key.round(2))
        }
    }
    is HSLColor -> {
        assertThat(this).all {
            assertThat(hue.round(2)).isEqualTo((other as HSLColor).hue.round(2))
            assertThat(saturation.round(2)).isEqualTo((other as HSLColor).saturation.round(2))
            assertThat(lightness.round(2)).isEqualTo((other as HSLColor).lightness.round(2))
        }
    }
    is HSLAColor -> {
        assertThat(this).all {
            assertThat(hue.round(2)).isEqualTo((other as HSLAColor).hue.round(2))
            assertThat(saturation.round(2)).isEqualTo((other as HSLAColor).saturation.round(2))
            assertThat(lightness.round(2)).isEqualTo((other as HSLAColor).lightness.round(2))
            assertThat(alpha.round(2)).isEqualTo((other as HSLAColor).alpha.round(2))
        }
    }
    is HSVColor -> {
        assertThat(this).all {
            assertThat(hue.round(2)).isEqualTo((other as HSVColor).hue.round(2))
            assertThat(saturation.round(2)).isEqualTo((other as HSVColor).saturation.round(2))
            assertThat(value.round(2)).isEqualTo((other as HSVColor).value.round(2))
        }
    }
    else -> fail()
}

infix fun <T> Pair<T, T>.eqWithUnderstandablePrecisionLoss(other: Pair<T, T>) {
    first eqWithUnderstandablePrecisionLoss other.first
    second eqWithUnderstandablePrecisionLoss other.second
}

infix fun <T> Triple<T, T, T>.eqWithUnderstandablePrecisionLoss(other: Triple<T, T, T>) {
    first eqWithUnderstandablePrecisionLoss other.first
    second eqWithUnderstandablePrecisionLoss other.second
    third eqWithUnderstandablePrecisionLoss other.third
}

infix fun <T> List<T>.eqWithUnderstandablePrecisionLoss(other: List<T>) =
    assertThat(this).all {
        forEachIndexed { index, _ -> get(index) eqWithUnderstandablePrecisionLoss other[index] }
    }

fun Float.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}
