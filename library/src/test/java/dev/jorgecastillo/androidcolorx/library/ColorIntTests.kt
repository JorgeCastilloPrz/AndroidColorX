package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
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
    fun `is dark should return false for #ffffff`() {
        val color = Color.parseColor("#ffffff")

        assertFalse(color.isDark())
    }
}
