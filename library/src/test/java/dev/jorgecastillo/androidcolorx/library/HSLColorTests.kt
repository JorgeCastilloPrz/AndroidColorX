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
class HSLColorTests {

    @Test
    fun `is dark should return true for #e91e63`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        assertTrue(color.isDark())
    }

    @Test
    fun `is dark should return false for #1ee9a4`() {
        val color = HSLColor(159.61f, 0.82f, 0.52f)

        assertFalse(color.isDark())
    }

    @Test
    fun `lighten integer should enlighten the color`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        HSLColor(339.49f, 0.82f, 0.72f) eqWithUnderstandablePrecisionLoss
                color.lighten(20)
    }

    @Test
    fun `lighten float should enlighten the color`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        HSLColor(339.49f, 0.82f, 0.72f) eqWithUnderstandablePrecisionLoss
                color.lighten(0.2f)
    }

    @Test
    fun `darken integer should darken the color`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        HSLColor(339.70f, 0.82f, 0.32f) eqWithUnderstandablePrecisionLoss
                color.darken(20)
    }

    @Test
    fun `darken float should darken the color`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        HSLColor(339.70f, 0.82f, 0.32f) eqWithUnderstandablePrecisionLoss
                color.darken(0.2f)
    }

    @Test
    fun `darken integer should be equivalent to dark float`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        assertEquals(color.darken(20), color.darken(0.2f))
    }

    @Test
    fun `shades should be properly calculated`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        listOf(
            HSLColor(339.70f, 0.82f, 0.52f),
            HSLColor(339.49f, 0.82f, 0.47f),
            HSLColor(339.66f, 0.82f, 0.42f),
            HSLColor(339.87f, 0.82f, 0.36f)
        ) eqWithUnderstandablePrecisionLoss color.shades()
    }

    @Test
    fun `shades with specific count should be properly calculated`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        listOf(
            HSLColor(339.70f, 0.82f, 0.52f),
            HSLColor(339.72f, 0.82f, 0.35f),
            HSLColor(340f, 0.82f, 0.17f),
            HSLColor(0f, 0f, 0f)
        ) eqWithUnderstandablePrecisionLoss color.shades(count = 3)
    }

    @Test
    fun `tints should be properly calculated`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        listOf(
            HSLColor(339.70f, 0.82f, 0.52f),
            HSLColor(339.78f, 0.82f, 0.57f),
            HSLColor(339.75f, 0.82f, 0.62f),
            HSLColor(340.00f, 0.82f, 0.66f),
            HSLColor(339.67f, 0.82f, 0.71f),
            HSLColor(339.80f, 0.82f, 0.76f),
            HSLColor(339.75f, 0.82f, 0.81f),
            HSLColor(340.00f, 0.81f, 0.85f),
            HSLColor(339.51f, 0.84f, 0.90f),
            HSLColor(342.00f, 0.83f, 0.95f),
            HSLColor(0.00f, 0.00f, 1.00f)
        ) eqWithUnderstandablePrecisionLoss color.tints()
    }

    @Test
    fun `tints for specific count should be properly calculated`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        listOf(
            HSLColor(339.70f, 0.82f, 0.52f),
            HSLColor(339.4f, 0.82f, 0.68f),
            HSLColor(340.3f, 0.83f, 0.84f),
            HSLColor(0.00f, 0.00f, 1.00f)
        ) eqWithUnderstandablePrecisionLoss color.tints(count = 3)
    }

    @Test
    fun `complimentary colors should be calculated as expected`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        HSLColor(159.70f, 0.82f, 0.52f) eqWithUnderstandablePrecisionLoss
                color.complimentary()
    }

    @Test
    fun `returns white as contrasting color for dark colors`() {
        val color = HSLColor(339.7f, 0.82f, 0.52f)

        assertEquals(HSLColor(0f, 0f, 1f), color.contrasting())
    }

    @Test
    fun `returns black as contrasting color for light colors`() {
        val color = HSLColor(159.70f, 0.82f, 0.52f)

        assertEquals(HSLColor(0f, 0f, 0f), color.contrasting())
    }

    @Test
    fun `returns passed light color as contrasting color for dark colors`() {
        val color = HSLColor(339.7f, 0.82f, 0.52f)
        val lightColor = HSLColor(159.70f, 0.82f, 0.52f)

        assertEquals(
            lightColor,
            color.contrasting(
                lightColor = lightColor,
                darkColor = HSLColor(0f, 0f, 0f)
            )
        )
    }

    @Test
    fun `returns passed dark color as contrasting color for light colors`() {
        val color = HSLColor(159.70f, 0.82f, 0.52f)
        val darkColor = HSLColor(339.7f, 0.82f, 0.52f)

        assertEquals(
            darkColor,
            color.contrasting(
                lightColor = HSLColor(0f, 0f, 1f),
                darkColor = darkColor
            )
        )
    }

    @Test
    fun `triadic colors should be calculated as expected`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        assertEquals(
            Pair(
                HSLColor(99.609985f, 0.82f, 0.52f),
                HSLColor(219.60999f, 0.82f, 0.52f)
            ),
            color.triadic()
        )
    }

    @Test
    fun `tetradic colors should be calculated as expected`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        assertEquals(
            Triple(
                HSLColor(69.609985f, 0.82f, 0.52f),
                HSLColor(159.60999f, 0.82f, 0.52f),
                HSLColor(249.60999f, 0.82f, 0.52f)
            ),
            color.tetradic()
        )
    }

    @Test
    fun `analogous colors should be calculated as expected`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        Pair(
            HSLColor(9.85f, 0.82f, 0.52f),
            HSLColor(309.85f, 0.82f, 0.52f)
        ) eqWithUnderstandablePrecisionLoss Pair(
            color.analogous().first,
            color.analogous().second
        )
    }

    @Test
    fun `converts to ColorInt and back is idempotent with understandable precision loss`() {
        val color = HSLColor(339.7f, 0.82f, 0.52f)

        color eqWithUnderstandablePrecisionLoss color.asColorInt().asHsl()
    }

    @Test
    fun `converts to RGB and back is idempotent with understandable precision loss`() {
        val color = HSLColor(339.7f, 0.82f, 0.52f)

        color eqWithUnderstandablePrecisionLoss color.asRgb().asHsl()
    }

    @Test
    fun `converts to ARGB and back is idempotent with understandable precision loss`() {
        val color = HSLColor(339.7f, 0.82f, 0.52f)

        color eqWithUnderstandablePrecisionLoss color.asArgb().asHsl()
    }

    @Test
    fun `converts to ARGB assumes 255 alpha`() {
        val color = HSLColor(339.7f, 0.82f, 0.52f)

        assertEquals(255, color.asArgb().alpha)
    }

    @Test
    fun `converts to HEX and back is idempotent`() {
        val color = HSLColor(339.7f, 0.82f, 0.52f)

        color eqWithUnderstandablePrecisionLoss color.asHex().asHsl()
    }

    @Test
    fun `converts to HSLA and back is idempotent`() {
        val color = HSLColor(339.7f, 0.82f, 0.52f)

        color eqWithUnderstandablePrecisionLoss color.asHsla().asHsl()
    }

    @Test
    fun `converts to HSLA assumes 1f alpha`() {
        val color = HSLColor(339.7f, 0.82f, 0.52f)

        assertEquals(1f, color.asHsla().alpha)
    }

    @Test
    fun `converts to CMYK and back is idempotent`() {
        val color = HSLColor(339.7f, 0.82f, 0.52f)

        color eqWithUnderstandablePrecisionLoss color.asCmyk().asHsl()
    }

    @Test
    fun `converts to HSV and back is idempotent`() {
        val color = HSLColor(339.7f, 0.82f, 0.52f)

        color eqWithUnderstandablePrecisionLoss color.asHsv().asHsl()
    }
}
