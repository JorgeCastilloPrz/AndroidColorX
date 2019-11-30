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
class HSLAColorTests {

    @Test
    fun `is dark should return true for #e91e63`() {
        val color = HSLAColor(339.61f, 0.82f, 0.52f, 0.2f)

        assertTrue(color.isDark())
    }

    @Test
    fun `is dark should return false for #1ee9a4`() {
        val color = HSLAColor(159.61f, 0.82f, 0.52f, 0.2f)

        assertFalse(color.isDark())
    }

    @Test
    fun `lighten integer should enlighten the color`() {
        val color = HSLAColor(339.61f, 0.82f, 0.52f, 0.2f)

        HSLAColor(339.49f, 0.82f, 0.72f, 0.2f) withQuiteBigPrecisionLoss color.lighten(20)
    }

    @Test
    fun `lighten float should enlighten the color`() {
        val color = HSLAColor(339.61f, 0.82f, 0.52f, 0.2f)

        HSLAColor(339.49f, 0.82f, 0.72f, 0.2f) withQuiteBigPrecisionLoss color.lighten(0.2f)
    }

    @Test
    fun `darken integer should darken the color`() {
        val color = HSLAColor(339.61f, 0.82f, 0.52f, 0.2f)

        HSLAColor(339.70f, 0.82f, 0.32f, 0.2f) withQuiteBigPrecisionLoss color.darken(20)
    }

    @Test
    fun `darken float should darken the color`() {
        val color = HSLAColor(339.61f, 0.82f, 0.52f, 0.2f)

        HSLAColor(339.70f, 0.82f, 0.32f, 0.2f) withQuiteBigPrecisionLoss color.darken(0.2f)
    }

    @Test
    fun `darken integer should be equivalent to dark float`() {
        val color = HSLAColor(339.61f, 0.82f, 0.52f, 0.2f)

        assertEquals(color.darken(20), color.darken(0.2f))
    }

    @Test
    fun `shades should be properly calculated`() {
        val color = HSLAColor(339.61f, 0.82f, 0.52f, 0.2f)

        listOf(
            HSLAColor(339.70f, 0.82f, 0.52f, 0.2f),
            HSLAColor(339.49f, 0.82f, 0.47f, 0.2f),
            HSLAColor(339.66f, 0.82f, 0.42f, 0.2f),
            HSLAColor(339.87f, 0.82f, 0.36f, 0.2f),
            HSLAColor(339.85f, 0.82f, 0.31f, 0.2f),
            HSLAColor(339.63f, 0.82f, 0.26f, 0.2f),
            HSLAColor(339.77f, 0.81f, 0.21f, 0.2f),
            HSLAColor(339.69f, 0.82f, 0.15f, 0.2f),
            HSLAColor(340.47f, 0.81f, 0.10f, 0.2f),
            HSLAColor(338.18f, 0.85f, 0.05f, 0.2f),
            HSLAColor(0.00f, 0.00f, 0.00f, 0.2f)
        ) withQuiteBigPrecisionLoss color.shades()
    }

    @Test
    fun `shades with specific count should be properly calculated`() {
        val color = HSLAColor(339.61f, 0.82f, 0.52f, 0.2f)

        listOf(
            HSLAColor(339.70f, 0.82f, 0.52f, 0.2f),
            HSLAColor(339.72f, 0.82f, 0.35f, 0.2f),
            HSLAColor(340f, 0.82f, 0.17f, 0.2f),
            HSLAColor(0f, 0f, 0f, 0.2f)
        ) withQuiteBigPrecisionLoss color.shades(count = 3)
    }

    @Test
    fun `tints should be properly calculated`() {
        val color = HSLAColor(339.61f, 0.82f, 0.52f, 0.2f)

        listOf(
            HSLAColor(339.70f, 0.82f, 0.52f, 0.2f),
            HSLAColor(339.78f, 0.82f, 0.57f, 0.2f),
            HSLAColor(339.75f, 0.82f, 0.62f, 0.2f),
            HSLAColor(340.00f, 0.82f, 0.66f, 0.2f),
            HSLAColor(339.67f, 0.82f, 0.71f, 0.2f),
            HSLAColor(339.80f, 0.82f, 0.76f, 0.2f),
            HSLAColor(339.75f, 0.82f, 0.81f, 0.2f),
            HSLAColor(340.00f, 0.81f, 0.85f, 0.2f),
            HSLAColor(339.51f, 0.84f, 0.90f, 0.2f),
            HSLAColor(342.00f, 0.83f, 0.95f, 0.2f),
            HSLAColor(0.00f, 0.00f, 1.00f, 0.2f)
        ) withQuiteBigPrecisionLoss color.tints()
    }

    @Test
    fun `tints for specific count should be properly calculated`() {
        val color = HSLAColor(339.61f, 0.82f, 0.52f, 0.2f)

        listOf(
            HSLAColor(339.70f, 0.82f, 0.52f, 0.2f),
            HSLAColor(339.4f, 0.82f, 0.68f, 0.2f),
            HSLAColor(340.3f, 0.83f, 0.84f, 0.2f),
            HSLAColor(0.00f, 0.00f, 1.00f, 0.2f)
        ) withQuiteBigPrecisionLoss color.tints(count = 3)
    }

    @Test
    fun `complimentary colors should be calculated as expected`() {
        val color = HSLAColor(339.61f, 0.82f, 0.52f, 0.2f)

        HSLAColor(
            159.70f,
            0.82f,
            0.52f,
            0.2f
        ) withQuiteBigPrecisionLoss color.complimentary()
    }

    @Test
    fun `returns white as contrasting color for dark colors`() {
        val color = HSLAColor(339.7f, 0.82f, 0.52f, 0.2f)

        assertEquals(HSLAColor(0f, 0f, 1f, 1f), color.contrasting())
    }

    @Test
    fun `returns black as contrasting color for light colors`() {
        val color = HSLAColor(159.70f, 0.82f, 0.52f, 0.2f)

        assertEquals(HSLAColor(0f, 0f, 0f, 1f), color.contrasting())
    }

    @Test
    fun `returns passed light color as contrasting color for dark colors`() {
        val color = HSLAColor(339.7f, 0.82f, 0.52f, 0.2f)
        val lightColor = HSLAColor(159.70f, 0.82f, 0.52f, 0.2f)

        assertEquals(
            lightColor,
            color.contrasting(
                lightColor = lightColor,
                darkColor = HSLAColor(0f, 0f, 0f, 1f)
            )
        )
    }

    @Test
    fun `returns passed dark color as contrasting color for light colors`() {
        val color = HSLAColor(159.70f, 0.82f, 0.52f, 0.2f)
        val darkColor = HSLAColor(339.7f, 0.82f, 0.52f, 0.2f)

        assertEquals(
            darkColor,
            color.contrasting(
                lightColor = HSLAColor(0f, 0f, 1f, 1f),
                darkColor = darkColor
            )
        )
    }

    @Test
    fun `triadic colors should be calculated as expected`() {
        val color = HSLAColor(339.61f, 0.82f, 0.52f, 0.2f)

        assertEquals(
            Pair(
                HSLAColor(99.609985f, 0.82f, 0.52f, 0.2f),
                HSLAColor(219.60999f, 0.82f, 0.52f, 0.2f)
            ),
            color.triadic()
        )
    }

    @Test
    fun `tetradic colors should be calculated as expected`() {
        val color = HSLAColor(339.61f, 0.82f, 0.52f, 0.2f)

        assertEquals(
            Triple(
                HSLAColor(69.609985f, 0.82f, 0.52f, 0.2f),
                HSLAColor(159.60999f, 0.82f, 0.52f, 0.2f),
                HSLAColor(249.60999f, 0.82f, 0.52f, 0.2f)
            ),
            color.tetradic()
        )
    }

    @Test
    fun `analogous colors should be calculated as expected`() {
        val color = HSLAColor(339.61f, 0.82f, 0.52f, 0.2f)

        Pair(
            HSLAColor(9.85f, 0.82f, 0.52f, 0.2f),
            HSLAColor(309.85f, 0.82f, 0.52f, 0.2f)
        ) withQuiteBigPrecisionLoss color.analogous()
    }

    @Test
    fun `converts to ColorInt and back is idempotent with understandable precision loss`() {
        val color = HSLAColor(339.7f, 0.82f, 0.52f, 0.2f)

        color withQuiteBigPrecisionLoss color.asColorInt().asHsla()
    }

    @Test
    fun `converts to RGB and back just loses information about alpha`() {
        val color = HSLAColor(339.7f, 0.82f, 0.52f, 0.2f)

        color.copy(alpha = 1f) withQuiteBigPrecisionLoss color.asRgb().asHsla()
    }

    @Test
    fun `converts to ARGB and back is idempotent with understandable precision loss`() {
        val color = HSLAColor(339.7f, 0.82f, 0.52f, 0.2f)

        color withQuiteBigPrecisionLoss color.asArgb().asHsla()
    }

    @Test
    fun `converts to HEX and back is idempotent`() {
        val color = HSLAColor(339.7f, 0.82f, 0.52f, 0.2f)

        color withQuiteBigPrecisionLoss color.asHex().asHsla()
    }

    @Test
    fun `converts to HSL and back just loses information about alpha`() {
        val color = HSLAColor(339.7f, 0.82f, 0.52f, 0.2f)

        assertEquals(
            color.copy(alpha = 1f),
            color.asHsl().asHsla()
        )
    }

    @Test
    fun `converts to CMYK and back just loses information about alpha`() {
        val color = HSLAColor(339.7f, 0.82f, 0.52f, 0.2f)

        color.copy(alpha = 1f) withQuiteBigPrecisionLoss color.asCmyk().asHsla()
    }

    @Test
    fun `converts to HSV and back just loses information about alpha`() {
        val color = HSLAColor(339.7f, 0.82f, 0.52f, 0.2f)

        color.copy(alpha = 1f) withQuiteBigPrecisionLoss color.asHsv().asHsla()
    }
}
