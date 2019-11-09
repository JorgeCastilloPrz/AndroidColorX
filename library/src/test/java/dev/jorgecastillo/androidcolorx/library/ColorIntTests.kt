package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
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
class ColorIntTests {

    @Test
    fun `is dark should return true for #e91e63`() {
        val color = Color.parseColor("#e91e63")

        assertTrue(color.isDark())
    }

    @Test
    fun `is dark should return false for #1ee9a4`() {
        val color = Color.parseColor("#1ee9a4")

        assertFalse(color.isDark())
    }

    @Test
    fun `lighten integer should enlighten the color`() {
        val color = Color.parseColor("#e91e63")

        assertEquals(-885853, color.lighten(20))
    }

    @Test
    fun `lighten float should enlighten the color`() {
        val color = Color.parseColor("#e91e63")

        assertEquals(-885853, color.lighten(0.2f))
    }

    @Test
    fun `darken integer should darken the color`() {
        val color = Color.parseColor("#e91e63")

        assertEquals(-7139781, color.darken(20))
    }

    @Test
    fun `darken float should darken the color`() {
        val color = Color.parseColor("#e91e63")

        assertEquals(-7139781, color.darken(0.2f))
    }

    @Test
    fun `darken integer should be equivalent to dark float`() {
        val color = Color.parseColor("#e91e63")

        assertEquals(color.darken(20), color.darken(0.2f))
    }

    @Test
    fun `shades should be properly calculated`() {
        val color = Color.parseColor("#e91e63")

        assertEquals(
            listOf(
                -1499549,
                -2615977,
                -4189362,
                -5763004,
                -7336390,
                -8909776,
                -10483417,
                -12056803,
                -13630189,
                -15203830,
                -16777216
            ),
            color.getShades())
    }

    @Test
    fun `tints should be properly calculated`() {
        val color = Color.parseColor("#e91e63")

        assertEquals(
            listOf(
                -1499549,
                -1362829,
                -1225854,
                -1023598,
                -886623,
                -749903,
                -612927,
                -476208,
                -273696,
                -136977,
                -1
            ),
            color.getTints())
    }

    @Test
    fun `complimentary colors should be calculated as expected`() {
        val color = Color.parseColor("#e91e63")

        assertEquals(-14751324, color.complimentary())
    }

    @Test
    fun `triadic colors should be calculated as expected`() {
        val color = Color.parseColor("#e91e63")

        assertEquals(
            Pair(-10229474, -14785559),
            color.triadic()
        )
    }

    @Test
    fun `tetradic colors should be calculated as expected`() {
        val color = Color.parseColor("#e91e63")

        assertEquals(
            Triple(-3544802, -14751324, -12706071),
            color.tetradic()
        )
    }

    @Test
    fun `analogous colors should be calculated as expected`() {
        val color = Color.parseColor("#e91e63")

        assertEquals(
            Pair(-1491426, -1499447),
            color.analogous()
        )
    }
}
