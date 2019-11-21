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
class CMYKColorTests {

    @Test
    fun `is dark should return true for #e91e63`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        assertTrue(color.isDark())
    }

    @Test
    fun `is dark should return false for #1ee9a4`() {
        val color = CMYKColor(0.87f, 0f, 0.30f, 0.09f)

        assertFalse(color.isDark())
    }

    @Test
    fun `lighten integer should enlighten the color`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        CMYKColor(0f, 0.49f, 0.33f, 0.05f) eqWithUnderstandablePrecisionLoss
                color.lighten(20)
    }

    @Test
    fun `lighten float should enlighten the color`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        CMYKColor(0f, 0.49f, 0.33f, 0.05f) eqWithUnderstandablePrecisionLoss
                color.lighten(0.2f)
    }

    @Test
    fun `darken integer should darken the color`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        CMYKColor(0f, 0.90f, 0.60f, 0.43f) eqWithUnderstandablePrecisionLoss
                color.darken(20)
    }

    @Test
    fun `darken float should darken the color`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        CMYKColor(0f, 0.90f, 0.60f, 0.43f) eqWithUnderstandablePrecisionLoss
                color.darken(0.2f)
    }

    @Test
    fun `darken integer should be equivalent to dark float`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        assertEquals(color.darken(20), color.darken(0.2f))
    }

    @Test
    fun `shades should be properly calculated`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        listOf(
            CMYKColor(0f, 0.87f, 0.58f, 0.09f),
            CMYKColor(0f, 0.90f, 0.60f, 0.16f),
            CMYKColor(0f, 0.90f, 0.60f, 0.25f),
            CMYKColor(0f, 0.90f, 0.60f, 0.35f),
            CMYKColor(0f, 0.90f, 0.60f, 0.44f),
            CMYKColor(0f, 0.90f, 0.60f, 0.53f),
            CMYKColor(0f, 0.89f, 0.60f, 0.63f),
            CMYKColor(0f, 0.90f, 0.59f, 0.72f),
            CMYKColor(0f, 0.90f, 0.60f, 0.81f),
            CMYKColor(0f, 0.92f, 0.58f, 0.91f),
            CMYKColor(0f, 0f, 0f, 1.00f)
        ) eqWithUnderstandablePrecisionLoss color.shades()
    }

    @Test
    fun `tints should be properly calculated`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        listOf(
            CMYKColor(0f, 0.87f, 0.58f, 0.09f),
            CMYKColor(0f, 0.78f, 0.52f, 0.08f),
            CMYKColor(0f, 0.68f, 0.46f, 0.07f),
            CMYKColor(0f, 0.59f, 0.40f, 0.06f),
            CMYKColor(0f, 0.50f, 0.34f, 0.05f),
            CMYKColor(0f, 0.42f, 0.28f, 0.04f),
            CMYKColor(0f, 0.33f, 0.22f, 0.04f),
            CMYKColor(0f, 0.25f, 0.16f, 0.03f),
            CMYKColor(0f, 0.16f, 0.11f, 0.02f),
            CMYKColor(0f, 0.08f, 0.06f, 0.01f),
            CMYKColor(0f, 0f, 0f, 0.00f)
        ) eqWithUnderstandablePrecisionLoss color.tints()
    }

    @Test
    fun `complimentary colors should be calculated as expected`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        CMYKColor(
            0.87f,
            0.00f,
            0.29f,
            0.09f
        ) eqWithUnderstandablePrecisionLoss color.complimentary()
    }

    @Test
    fun `triadic colors should be calculated as expected`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        Pair(
            CMYKColor(0.58f, 0f, 0.87f, 0.09f),
            CMYKColor(0.87f, 0.58f, 0.00f, 0.09f)
        ) eqWithUnderstandablePrecisionLoss color.triadic()
    }

    @Test
    fun `tetradic colors should be calculated as expected`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        Triple(
            CMYKColor(0.15f, 0f, 0.87f, 0.09f),
            CMYKColor(0.87f, 0f, 0.29f, 0.09f),
            CMYKColor(0.72f, 0.87f, 0f, 0.09f)
        ) eqWithUnderstandablePrecisionLoss color.tetradic()
    }

    @Test
    fun `analogous colors should be calculated as expected`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        Pair(
            CMYKColor(0f, 0.72f, 0.87f, 0.09f),
            CMYKColor(0f, 0.87f, 0.15f, 0.09f)
        ) eqWithUnderstandablePrecisionLoss color.analogous()
    }

    @Test
    fun `converts to RGB and back is idempotent with understandable precision loss`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        color eqWithUnderstandablePrecisionLoss color.asRgb().asCmyk()
    }

    @Test
    fun `converts to ARGB and back is idempotent with understandable precision loss`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        color eqWithUnderstandablePrecisionLoss color.asArgb().asCmyk()
    }

    @Test
    fun `converts to ColorInt and back is idempotent with understandable precision loss`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        color eqWithUnderstandablePrecisionLoss color.asColorInt().asCmyk()
    }

    @Test
    fun `converts to HSL and back is idempotent with understandable precision loss`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        color eqWithUnderstandablePrecisionLoss color.asHsl().asCmyk()
    }

    @Test
    fun `converts to HSLA and back is idempotent with understandable precision loss`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        color eqWithUnderstandablePrecisionLoss color.asHsla().asCmyk()
    }

    @Test
    fun `converts to HEX and back is idempotent with understandable precision loss`() {
        val color = CMYKColor(0f, 0.87f, 0.58f, 0.09f)

        color eqWithUnderstandablePrecisionLoss color.asHex().asCmyk()
    }
}
