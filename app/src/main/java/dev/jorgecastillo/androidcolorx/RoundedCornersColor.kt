package dev.jorgecastillo.androidcolorx

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.google.android.material.shape.MaterialShapeDrawable

class RoundedCornersColor @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private val DEFAULT_COLOR = Color.parseColor("#FFE0B2")
    }

    init {
        background = MaterialShapeDrawable().apply {
            setCornerRadius(resources.getDimensionPixelSize(R.dimen.card_corner_radius).toFloat())
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.RoundedCornersColor)
            val color = attributes.getColor(R.styleable.RoundedCornersColor_color, DEFAULT_COLOR)
            attributes.recycle()
            fillColor = ColorStateList.valueOf(color)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    fun setColor(@ColorInt color: Int) {
        (background as MaterialShapeDrawable).fillColor = ColorStateList.valueOf(color)
    }
}
