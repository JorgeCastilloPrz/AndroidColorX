package dev.jorgecastillo.androidcolorx.library

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
class HSVColorTests {

    @Test
    fun `is dark should return true for #e91e63`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        assertTrue(color.isDark())
    }

    @Test
    fun `is dark should return false for #1ee9a4`() {
        val color = HSVColor(290f, 0.17f, 1f)

        assertFalse(color.isDark())
    }

    @Test
    fun `lighten integer should enlighten the color`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        HSVColor(339.43f, 0.81f, 0.85f) eqWithUnderstandablePrecisionLoss
                color.lighten(20)
    }

    @Test
    fun `lighten float should enlighten the color`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        HSVColor(339.43f, 0.81f, 0.85f) eqWithUnderstandablePrecisionLoss
                color.lighten(0.2f)
    }

    @Test
    fun `lighten integer should be equivalent to lighten float`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        assertEquals(color.lighten(20), color.lighten(0.2f))
    }

    @Test
    fun `darken integer should darken the color`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        HSVColor(340f, 0.83f, 0.18f) eqWithUnderstandablePrecisionLoss
                color.darken(20)
    }

    @Test
    fun `darken float should darken the color`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        HSVColor(340f, 0.83f, 0.18f) eqWithUnderstandablePrecisionLoss
                color.darken(0.2f)
    }

    @Test
    fun `darken integer should be equivalent to dark float`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        assertEquals(color.darken(20), color.darken(0.2f))
    }

    @Test
    fun `shades should be properly calculated`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        listOf(
            HSVColor(339.63f, 0.82f, 0.52f),
            HSVColor(339.8f, 0.82f, 0.47f),
            HSVColor(339.31f, 0.82f, 0.42f),
            HSVColor(339.47f, 0.82f, 0.36f),
            HSVColor(339.09f, 0.82f, 0.31f),
            HSVColor(339.27f, 0.82f, 0.26f),
            HSVColor(340.47f, 0.81f, 0.21f),
            HSVColor(340f, 0.82f, 0.16f),
            HSVColor(340.91f, 0.81f, 0.11f),
            HSVColor(338.18f, 0.85f, 0.05f),
            HSVColor(0.00f, 0.00f, 0.00f)
        ) eqWithUnderstandablePrecisionLoss color.shades()
    }

    @Test
    fun `shades with specific count should be properly calculated`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        listOf(
            HSVColor(339.63f, 0.82f, 0.52f),
            HSVColor(339.45f, 0.82f, 0.35f),
            HSVColor(340f, 0.82f, 0.17f),
            HSVColor(0f, 0f, 0f)
        ) eqWithUnderstandablePrecisionLoss color.shades(count = 3)
    }

    @Test
    fun `tints should be properly calculated`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        listOf(
            HSVColor(339.63f, 0.82f, 0.52f),
            HSVColor(339.4f, 0.82f, 0.64f),
            HSVColor(339.87f, 0.82f, 0.76f),
            HSVColor(339.65f, 0.79f, 0.85f),
            HSVColor(340.0f, 0.66f, 0.87f),
            HSVColor(339.51f, 0.54f, 0.89f),
            HSVColor(339.18f, 0.42f, 0.91f),
            HSVColor(339.73f, 0.31f, 0.94f),
            HSVColor(339.18f, 0.2f, 0.96f),
            HSVColor(340.8f, 0.1f, 0.98f),
            HSVColor(0.00f, 0.00f, 1.00f)
        ) eqWithUnderstandablePrecisionLoss color.tints()
    }

    @Test
    fun `tints with specific count should be properly calculated`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        listOf(
            HSVColor(339.63f, 0.82f, 0.52f),
            HSVColor(339.75f, 0.74f, 0.86f),
            HSVColor(339.51f, 0.35f, 0.93f),
            HSVColor(0.00f, 0.00f, 1.00f)
        ) eqWithUnderstandablePrecisionLoss color.tints(count = 3)
    }

    @Test
    fun `complimentary colors should be calculated as expected`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        HSVColor(159.63f, 0.82f, 0.52f) eqWithUnderstandablePrecisionLoss
                color.complimentary()
    }

    @Test
    fun `returns white as contrasting color for dark colors`() {
        val color = HSVColor(339.7f, 0.82f, 0.52f)

        assertEquals(HSVColor(0f, 0f, 1f), color.contrasting())
    }

    @Test
    fun `returns black as contrasting color for light colors`() {
        val color = HSVColor(290f, 0.17f, 1f)

        assertEquals(HSVColor(0f, 0f, 0f), color.contrasting())
    }

    @Test
    fun `returns passed light color as contrasting color for dark colors`() {
        val color = HSVColor(339.7f, 0.82f, 0.52f)
        val lightColor = HSVColor(290f, 0.17f, 1f)

        assertEquals(
            lightColor,
            color.contrasting(
                lightColor = lightColor,
                darkColor = HSVColor(0f, 0f, 0f)
            )
        )
    }

    @Test
    fun `returns passed dark color as contrasting color for light colors`() {
        val color = HSVColor(290f, 0.17f, 1f)
        val darkColor = HSVColor(339.7f, 0.82f, 0.52f)

        assertEquals(
            darkColor,
            color.contrasting(
                lightColor = HSVColor(0f, 0f, 1f),
                darkColor = darkColor
            )
        )
    }

    @Test
    fun `triadic colors should be calculated as expected`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        Pair(
            HSVColor(99.63f, 0.82f, 0.52f),
            HSVColor(219.63f, 0.82f, 0.52f)
        ) eqWithUnderstandablePrecisionLoss Pair(
            color.triadic().first,
            color.triadic().second
        )
    }

    @Test
    fun `tetradic colors should be calculated as expected`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        Triple(
            HSVColor(69.36f, 0.82f, 0.52f),
            HSVColor(159.63f, 0.82f, 0.52f),
            HSVColor(249.91f, 0.82f, 0.52f)
        ) eqWithUnderstandablePrecisionLoss Triple(
            color.tetradic().first,
            color.tetradic().second,
            color.tetradic().third
        )
    }

    @Test
    fun `analogous colors should be calculated as expected`() {
        val color = HSVColor(339.61f, 0.82f, 0.52f)

        Pair(
            HSVColor(9.36f, 0.82f, 0.52f),
            HSVColor(309.91f, 0.82f, 0.52f)
        ) eqWithUnderstandablePrecisionLoss Pair(
            color.analogous().first,
            color.analogous().second
        )
    }

    @Test
    fun `converts to ColorInt and back is idempotent with understandable precision loss`() {
        val color = HSVColor(339.63f, 0.82f, 0.52f)

        color eqWithUnderstandablePrecisionLoss color.asColorInt().asHsv()
    }

    @Test
    fun `converts to RGB and back is idempotent with understandable precision loss`() {
        val color = HSVColor(339.63f, 0.82f, 0.52f)

        color eqWithUnderstandablePrecisionLoss color.asRgb().asHsv()
    }

    @Test
    fun `converts to ARGB and back is idempotent with understandable precision loss`() {
        val color = HSVColor(339.63f, 0.82f, 0.52f)

        color eqWithUnderstandablePrecisionLoss color.asArgb().asHsv()
    }

    @Test
    fun `converts to ARGB assumes 255 alpha`() {
        val color = HSVColor(339.63f, 0.82f, 0.52f)

        assertEquals(255, color.asArgb().alpha)
    }

    @Test
    fun `converts to HEX and back is idempotent`() {
        val color = HSVColor(339.63f, 0.82f, 0.52f)

        color eqWithUnderstandablePrecisionLoss color.asHex().asHsv()
    }

    @Test
    fun `converts to HSL and back is idempotent`() {
        val color = HSVColor(339.63f, 0.82f, 0.52f)

        color eqWithUnderstandablePrecisionLoss color.asHsl().asHsv()
    }

    @Test
    fun `converts to HSLA and back is idempotent`() {
        val color = HSVColor(339.63f, 0.82f, 0.52f)

        color eqWithUnderstandablePrecisionLoss color.asHsla().asHsv()
    }

    @Test
    fun `converts to HSLA assumes 1f alpha`() {
        val color = HSVColor(339.63f, 0.82f, 0.52f)

        assertEquals(1f, color.asHsla().alpha)
    }

    @Test
    fun `converts to CMYK and back is idempotent`() {
        val color = HSVColor(339.63f, 0.82f, 0.52f)

        color eqWithUnderstandablePrecisionLoss color.asCmyk().asHsv()
    }
}
