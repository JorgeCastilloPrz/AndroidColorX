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
class HEXColorTests {

    @Test
    fun `is dark should return true for #e91e63`() {
        val color = HEXColor("#e91e63")

        assertTrue(color.isDark())
    }

    @Test
    fun `is dark should return false for #1ee9a4`() {
        val color = HEXColor("#1ee9a4")

        assertFalse(color.isDark())
    }

    @Test
    fun `lighten integer should enlighten the color`() {
        val color = HEXColor("#e91e63")

        assertEquals(HEXColor("#FFF27BA3"), color.lighten(20))
    }

    @Test
    fun `lighten float should enlighten the color`() {
        val color = HEXColor("#e91e63")

        assertEquals(HEXColor("#FFF27BA3"), color.lighten(0.2f))
    }

    @Test
    fun `darken integer should darken the color`() {
        val color = HEXColor("#e91e63")

        assertEquals(HEXColor("#FF930E3B"), color.darken(20))
    }

    @Test
    fun `darken float should darken the color`() {
        val color = HEXColor("#e91e63")

        assertEquals(HEXColor("#FF930E3B"), color.darken(0.2f))
    }

    @Test
    fun `darken integer should be equivalent to dark float`() {
        val color = HEXColor("#e91e63")

        assertEquals(color.darken(20), color.darken(0.2f))
    }

    @Test
    fun `shades should be properly calculated`() {
        val color = HEXColor("#e91e63")

        assertEquals(
            listOf(
                HEXColor("#FFE91E63"),
                HEXColor("#FFD81557"),
                HEXColor("#FFC0134E"),
                HEXColor("#FFA81044"),
                HEXColor("#FF900E3A"),
                HEXColor("#FF780C30"),
                HEXColor("#FF600927"),
                HEXColor("#FF48071D"),
                HEXColor("#FF300513"),
                HEXColor("#FF18020A"),
                HEXColor("#FF000000")
            ),
            color.shades()
        )
    }

    @Test
    fun `tints should be properly calculated`() {
        val color = HEXColor("#e91e63")

        assertEquals(
            listOf(
                HEXColor("#FFE91E63"),
                HEXColor("#FFEB3473"),
                HEXColor("#FFED4B82"),
                HEXColor("#FFF06192"),
                HEXColor("#FFF278A1"),
                HEXColor("#FFF48EB1"),
                HEXColor("#FFF6A5C1"),
                HEXColor("#FFF8BBD0"),
                HEXColor("#FFFBD2E0"),
                HEXColor("#FFFDE8EF"),
                HEXColor("#FFFFFFFF")
            ),
            color.tints()
        )
    }

    @Test
    fun `complimentary colors should be calculated as expected`() {
        val color = HEXColor("#e91e63")

        assertEquals(HEXColor("#FF1EE9A4"), color.complimentary())
    }

    @Test
    fun `triadic colors should be calculated as expected`() {
        val color = HEXColor("#e91e63")

        assertEquals(
            Pair(HEXColor("#FF63E91E"), HEXColor("#FF1E63E9")),
            color.triadic()
        )
    }

    @Test
    fun `tetradic colors should be calculated as expected`() {
        val color = HEXColor("#e91e63")

        assertEquals(
            Triple(
                HEXColor("#FFC9E91E"),
                HEXColor("#FF1EE9A4"),
                HEXColor("#FF3E1EE9")
            ),
            color.tetradic()
        )
    }

    @Test
    fun `analogous colors should be calculated as expected`() {
        val color = HEXColor("#e91e63")

        assertEquals(
            Pair(HEXColor("#FFE93E1E"), HEXColor("#FFE91EC9")),
            color.analogous()
        )
    }
}
