package dev.jorgecastillo.androidcolorx.library

import android.graphics.Color
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
class ColorIntTests {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val color = Color.parseColor("#e91e63")
        color
    }
}
