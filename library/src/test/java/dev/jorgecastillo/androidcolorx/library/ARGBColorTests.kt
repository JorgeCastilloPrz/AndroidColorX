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
class ARGBColorTests {

    @Test
    fun `is dark should return true for #e91e63`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertTrue(color.isDark())
    }

    @Test
    fun `is dark should return false for #1ee9a4`() {
        val color = ARGBColor(20, 30, 233, 164)

        assertFalse(color.isDark())
    }

    @Test
    fun `lighten integer should enlighten the color`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(ARGBColor(20, 242, 123, 163), color.lighten(20))
    }

    @Test
    fun `lighten float should enlighten the color`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(ARGBColor(20, 242, 123, 163), color.lighten(0.2f))
    }

    @Test
    fun `darken integer should darken the color`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(ARGBColor(20, 147, 14, 59), color.darken(20))
    }

    @Test
    fun `darken float should darken the color`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(ARGBColor(20, 147, 14, 59), color.darken(0.2f))
    }

    @Test
    fun `darken integer should be equivalent to dark float`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(color.darken(20), color.darken(0.2f))
    }

    @Test
    fun `shades should be properly calculated`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(
            listOf(
                ARGBColor(20, 233, 30, 99),
                ARGBColor(20, 216, 21, 87),
                ARGBColor(20, 192, 19, 78),
                ARGBColor(20, 168, 16, 68),
                ARGBColor(20, 144, 14, 58),
                ARGBColor(20, 120, 12, 48),
                ARGBColor(20, 96, 9, 39),
                ARGBColor(20, 72, 7, 29),
                ARGBColor(20, 48, 5, 19),
                ARGBColor(20, 24, 2, 10),
                ARGBColor(20, 0, 0, 0)
            ),
            color.shades()
        )
    }

    @Test
    fun `tints should be properly calculated`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(
            listOf(
                ARGBColor(20, 233, 30, 99),
                ARGBColor(20, 235, 52, 115),
                ARGBColor(20, 237, 75, 130),
                ARGBColor(20, 240, 97, 146),
                ARGBColor(20, 242, 120, 161),
                ARGBColor(20, 244, 142, 177),
                ARGBColor(20, 246, 165, 193),
                ARGBColor(20, 248, 187, 208),
                ARGBColor(20, 251, 210, 224),
                ARGBColor(20, 253, 232, 239),
                ARGBColor(20, 255, 255, 255)
            ),
            color.tints()
        )
    }

    @Test
    fun `complimentary colors should be calculated as expected`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(ARGBColor(20, 30, 233, 164), color.complimentary())
    }

    @Test
    fun `triadic colors should be calculated as expected`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(
            Pair(ARGBColor(20, 99, 233, 30), ARGBColor(20, 30, 99, 233)),
            color.triadic()
        )
    }

    @Test
    fun `tetradic colors should be calculated as expected`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(
            Triple(
                ARGBColor(20, 201, 233, 30),
                ARGBColor(20, 30, 233, 164),
                ARGBColor(20, 62, 30, 233)
            ),
            color.tetradic()
        )
    }

    @Test
    fun `analogous colors should be calculated as expected`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(
            Pair(ARGBColor(20, 233, 62, 30), ARGBColor(20, 233, 30, 201)),
            color.analogous()
        )
    }

    @Test
    fun `converts to RGB and back just loses information about alpha`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(color.copy(alpha = 255), color.asRgb().asArgb())
    }

    @Test
    fun `converts to HEX and back is idempotent`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(color, color.asHex().asArgb())
    }

    @Test
    fun `converts to ColorInt and back is idempotent`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(color, color.asColorInt().asArgb())
    }

    @Test
    fun `converts to HSL and back is just loses information about alpha`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(color.copy(alpha = 255), color.asHsl().asArgb())
    }

    @Test
    fun `converts to HSLA and back is idempotent`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(color, color.asHsla().asArgb())
    }

    @Test
    fun `converts to CMYK and back just loses information about alpha`() {
        val color = ARGBColor(20, 233, 30, 99)

        assertEquals(color.copy(alpha = 255), color.asCmyk().asArgb())
    }
}
