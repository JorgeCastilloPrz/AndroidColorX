package dev.jorgecastillo.androidcolorx.library

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThanOrEqualTo
import org.junit.Assert.fail
import kotlin.math.abs
import kotlin.math.round

infix fun <T> T.eqWithBigPrecisionLoss(other: T): Unit = when (this) {
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

infix fun <T> Pair<T, T>.eqWithBigPrecisionLoss(other: Pair<T, T>) {
    first eqWithBigPrecisionLoss other.first
    second eqWithBigPrecisionLoss other.second
}

infix fun <T> Triple<T, T, T>.eqWithBigPrecisionLoss(other: Triple<T, T, T>) {
    first eqWithBigPrecisionLoss other.first
    second eqWithBigPrecisionLoss other.second
    third eqWithBigPrecisionLoss other.third
}

infix fun <T> List<T>.eqWithBigPrecisionLoss(other: List<T>) =
    assertThat(this).all {
        forEachIndexed { index, _ -> get(index) eqWithBigPrecisionLoss other[index] }
    }

fun Float.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

private const val MIN_ERROR_MARGIN = 0.000001f

infix fun <T> T.eqWithMinimumPrecisionLoss(other: T): Unit = when (this) {
    is CMYKColor -> {
        assertThat(this).all {
            assertThat(abs(cyan - (other as CMYKColor).cyan)).isLessThanOrEqualTo(MIN_ERROR_MARGIN)
            assertThat(abs(magenta - (other as CMYKColor).magenta)).isLessThanOrEqualTo(MIN_ERROR_MARGIN)
            assertThat(abs(yellow - (other as CMYKColor).yellow)).isLessThanOrEqualTo(MIN_ERROR_MARGIN)
            assertThat(abs(key - (other as CMYKColor).key)).isLessThanOrEqualTo(MIN_ERROR_MARGIN)
        }
    }
    is HSLColor -> {
        assertThat(this).all {
            assertThat(abs(hue - (other as HSLColor).hue)).isLessThanOrEqualTo(MIN_ERROR_MARGIN)
            assertThat(abs(saturation - (other as HSLColor).saturation)).isLessThanOrEqualTo(MIN_ERROR_MARGIN)
            assertThat(abs(lightness - (other as HSLColor).lightness)).isLessThanOrEqualTo(MIN_ERROR_MARGIN)
        }
    }
    is HSLAColor -> {
        assertThat(this).all {
            assertThat(abs(hue - (other as HSLAColor).hue)).isLessThanOrEqualTo(MIN_ERROR_MARGIN)
            assertThat(abs(saturation - (other as HSLAColor).saturation)).isLessThanOrEqualTo(MIN_ERROR_MARGIN)
            assertThat(abs(lightness - (other as HSLAColor).lightness)).isLessThanOrEqualTo(MIN_ERROR_MARGIN)
            assertThat(abs(alpha - (other as HSLAColor).alpha)).isLessThanOrEqualTo(MIN_ERROR_MARGIN)
        }
    }
    is HSVColor -> {
        assertThat(this).all {
            assertThat(abs(hue - (other as HSVColor).hue)).isLessThanOrEqualTo(MIN_ERROR_MARGIN)
            assertThat(abs(saturation - (other as HSVColor).saturation)).isLessThanOrEqualTo(MIN_ERROR_MARGIN)
            assertThat(abs(value - (other as HSVColor).value)).isLessThanOrEqualTo(MIN_ERROR_MARGIN)
        }
    }
    else -> fail()
}

infix fun <T> Pair<T, T>.eqWithMinimumPrecisionLoss(other: Pair<T, T>) {
    first eqWithMinimumPrecisionLoss other.first
    second eqWithMinimumPrecisionLoss other.second
}

infix fun <T> Triple<T, T, T>.eqWithMinimumPrecisionLoss(other: Triple<T, T, T>) {
    first eqWithMinimumPrecisionLoss other.first
    second eqWithMinimumPrecisionLoss other.second
    third eqWithMinimumPrecisionLoss other.third
}

infix fun <T> List<T>.eqWithMinimumPrecisionLoss(other: List<T>) =
    assertThat(this).all {
        forEachIndexed { index, _ -> get(index) eqWithBigPrecisionLoss other[index] }
    }
