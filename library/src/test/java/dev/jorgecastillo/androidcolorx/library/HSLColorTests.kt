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

        assertEquals(HSLColor(339.49f, 0.82f, 0.72f), color.lighten(20))
    }

    @Test
    fun `lighten float should enlighten the color`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        assertEquals(HSLColor(339.49f, 0.82f, 0.72f), color.lighten(0.2f))
    }

    @Test
    fun `darken integer should darken the color`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        assertEquals(HSLColor(339.70f, 0.82f, 0.32f), color.darken(20))
    }

    @Test
    fun `darken float should darken the color`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        assertEquals(HSLColor(339.70f, 0.82f, 0.32f), color.darken(0.2f))
    }

    @Test
    fun `darken integer should be equivalent to dark float`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        assertEquals(color.darken(20), color.darken(0.2f))
    }

    @Test
    fun `shades should be properly calculated`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        assertEquals(
            listOf(
                HSLColor(339.70f, 0.82f, 0.52f),
                HSLColor(339.49f, 0.82f, 0.47f),
                HSLColor(339.66f, 0.82f, 0.42f),
                HSLColor(339.87f, 0.82f, 0.36f),
                HSLColor(339.85f, 0.82f, 0.31f),
                HSLColor(339.63f, 0.82f, 0.26f),
                HSLColor(339.77f, 0.81f, 0.21f),
                HSLColor(339.69f, 0.82f, 0.15f),
                HSLColor(340.47f, 0.81f, 0.10f),
                HSLColor(338.18f, 0.85f, 0.05f),
                HSLColor(0.00f, 0.00f, 0.00f)
            ),
            color.shades().map { it }
        )
    }

    @Test
    fun `tints should be properly calculated`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        assertEquals(
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
            ),
            color.tints().map { it }
        )
    }

    @Test
    fun `complimentary colors should be calculated as expected`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        assertEquals(HSLColor(159.70f, 0.82f, 0.52f), color.complimentary())
    }

    @Test
    fun `triadic colors should be calculated as expected`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        assertEquals(
            Pair(
                HSLColor(99.70f, 0.82f, 0.52f),
                HSLColor(219.70f, 0.82f, 0.52f)
            ),
            Pair(
                color.triadic().first,
                color.triadic().second
            )
        )
    }

    @Test
    fun `tetradic colors should be calculated as expected`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        assertEquals(
            Triple(
                HSLColor(69.85f, 0.82f, 0.52f),
                HSLColor(159.70f, 0.82f, 0.52f),
                HSLColor(249.85f, 0.82f, 0.52f)
            ),
            Triple(
                color.tetradic().first,
                color.tetradic().second,
                color.tetradic().third
            )
        )
    }

    @Test
    fun `analogous colors should be calculated as expected`() {
        val color = HSLColor(339.61f, 0.82f, 0.52f)

        assertEquals(
            Pair(
                HSLColor(9.85f, 0.82f, 0.52f),
                HSLColor(309.85f, 0.82f, 0.52f)
            ),
            Pair(
                color.analogous().first,
                color.analogous().second
            )
        )
    }
}
