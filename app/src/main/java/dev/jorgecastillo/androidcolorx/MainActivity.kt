package dev.jorgecastillo.androidcolorx

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jorgecastillo.androidcolorx.library.analogous
import dev.jorgecastillo.androidcolorx.library.complimentary
import dev.jorgecastillo.androidcolorx.library.getShades
import dev.jorgecastillo.androidcolorx.library.getTints
import dev.jorgecastillo.androidcolorx.library.isDark
import dev.jorgecastillo.androidcolorx.library.tetradic
import dev.jorgecastillo.androidcolorx.library.toCMYK
import dev.jorgecastillo.androidcolorx.library.toHSL
import dev.jorgecastillo.androidcolorx.library.toHex
import dev.jorgecastillo.androidcolorx.library.toRGB
import dev.jorgecastillo.androidcolorx.library.triadic
import kotlinx.android.synthetic.main.activity_main.analogousColor1
import kotlinx.android.synthetic.main.activity_main.analogousColor1Hex
import kotlinx.android.synthetic.main.activity_main.analogousColor2
import kotlinx.android.synthetic.main.activity_main.analogousColor2Hex
import kotlinx.android.synthetic.main.activity_main.analogousColorBase
import kotlinx.android.synthetic.main.activity_main.analogousColorBaseHex
import kotlinx.android.synthetic.main.activity_main.analogousColorsTitle
import kotlinx.android.synthetic.main.activity_main.appBarLayout
import kotlinx.android.synthetic.main.activity_main.colorShadesList
import kotlinx.android.synthetic.main.activity_main.complimentaryColor
import kotlinx.android.synthetic.main.activity_main.complimentaryColorBase
import kotlinx.android.synthetic.main.activity_main.complimentaryColorBaseHex
import kotlinx.android.synthetic.main.activity_main.complimentaryColorHex
import kotlinx.android.synthetic.main.activity_main.complimentaryColorTitle
import kotlinx.android.synthetic.main.activity_main.selectedColorHex
import kotlinx.android.synthetic.main.activity_main.shadesTitle
import kotlinx.android.synthetic.main.activity_main.tetradicColor1
import kotlinx.android.synthetic.main.activity_main.tetradicColor1Hex
import kotlinx.android.synthetic.main.activity_main.tetradicColor2
import kotlinx.android.synthetic.main.activity_main.tetradicColor2Hex
import kotlinx.android.synthetic.main.activity_main.tetradicColor3
import kotlinx.android.synthetic.main.activity_main.tetradicColor3Hex
import kotlinx.android.synthetic.main.activity_main.tetradicColorBase
import kotlinx.android.synthetic.main.activity_main.tetradicColorBaseHex
import kotlinx.android.synthetic.main.activity_main.tetradicColorsTitle
import kotlinx.android.synthetic.main.activity_main.tintsList
import kotlinx.android.synthetic.main.activity_main.tintsTitle
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_main.triadicColor1
import kotlinx.android.synthetic.main.activity_main.triadicColor1Hex
import kotlinx.android.synthetic.main.activity_main.triadicColor2
import kotlinx.android.synthetic.main.activity_main.triadicColor2Hex
import kotlinx.android.synthetic.main.activity_main.triadicColorBase
import kotlinx.android.synthetic.main.activity_main.triadicColorBaseHex
import kotlinx.android.synthetic.main.activity_main.triadicColorsTitle

class MainActivity : AppCompatActivity() {

    private lateinit var menu: Menu

    @ColorInt
    private fun selectedColor(): Int = Color.parseColor("#e91e63")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupStatusBar()

        val selectedColor = selectedColor()
        appBarLayout.setBackgroundColor(selectedColor)
        val headerTextColor = ContextCompat.getColor(
            this,
            if (selectedColor.isDark()) R.color.white else R.color.black
        )
        selectedColorHex.setTextColor(headerTextColor)
        selectedColorHex.text = selectedColor.toHex()

        generateColors(selectedColor)
    }

    private fun setupStatusBar() {
        title = ""
        setSupportActionBar(toolbar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = selectedColor()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val favIcon = menu.findItem(R.id.favColor)
        val copyToClipboardItem = menu.findItem(R.id.copyToClipBoard)
        renderMenuIcons(favIcon, copyToClipboardItem)

        this.menu = menu
        return true
    }

    private fun renderMenuIcons(favIcon: MenuItem, copyToClipboardItem: MenuItem) {
        if (selectedColor().isDark()) {
            renderClipboardIcon(copyToClipboardItem, R.drawable.ic_content_copy_white_24dp)
            renderFavIcon(
                favIcon,
                R.drawable.ic_favorite_white_24dp
            )
        } else {
            renderClipboardIcon(copyToClipboardItem, R.drawable.ic_content_copy_black_24dp)
            renderFavIcon(
                favIcon,
                R.drawable.ic_favorite_black_24dp
            )
        }
    }

    private fun renderClipboardIcon(clipboardItem: MenuItem, @DrawableRes iconRes: Int) {
        clipboardItem.icon = ContextCompat.getDrawable(this, iconRes)
    }

    @Suppress("SENSELESS_COMPARISON")
    private fun renderFavIcon(favIcon: MenuItem, @DrawableRes iconRes: Int) {
        favIcon.actionView = null
        favIcon.icon = ContextCompat.getDrawable(this, iconRes)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favColor -> {
                true
            }
            R.id.copyToClipBoard -> {
                val anchorView = findViewById<View>(R.id.copyToClipBoard)
                val popup = PopupMenu(this, anchorView)
                val options = resources.getStringArray(R.array.copy_modes)
                options.forEachIndexed { index, action ->
                    popup.menu.add(0, index, index, action)
                }
                popup.setOnMenuItemClickListener {
                    when (it.itemId) {
                        0 -> copyToClipboard(selectedColor().toRGB())
                        1 -> copyToClipboard(selectedColor().toHex())
                        2 -> copyToClipboard(selectedColor().toCMYK())
                        3 -> copyToClipboard(selectedColor().toHSL())
                    }
                    true
                }
                popup.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun generateColors(selectedColor: Int) {
        generateComplimentary(selectedColor)
        generateShades(selectedColor)
        generateTints(selectedColor)
        generateAnalogous(selectedColor)
        generateTriadic(selectedColor)
        generateTetradic(selectedColor)
    }

    private fun generateShades(selectedColor: Int) {
        val adapter = GeneratedColorsAdapter()
        colorShadesList.adapter = adapter
        colorShadesList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        colorShadesList.setHasFixedSize(true)
        adapter.colors = selectedColor.getShades()
        shadesTitle.text = resources.getString(R.string.shades)
    }

    private fun generateTints(selectedColor: Int) {
        val adapter = GeneratedColorsAdapter()
        tintsList.adapter = adapter
        tintsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        tintsList.setHasFixedSize(true)
        adapter.colors = selectedColor.getTints()
        tintsTitle.text = resources.getString(R.string.tints)
    }

    private fun generateComplimentary(selectedColor: Int) {
        val hexColor = selectedColor.toHex()
        complimentaryColorTitle.text = resources.getString(R.string.complimentary)
        complimentaryColorBase.setColor(selectedColor)
        complimentaryColor.setColor(selectedColor.complimentary())

        val hexColorComplimentary = selectedColor.complimentary().toHex()
        complimentaryColorBaseHex.text = hexColor
        complimentaryColorHex.text = hexColorComplimentary
    }

    private fun generateAnalogous(selectedColor: Int) {
        val hexColor = selectedColor.toHex()
        analogousColorsTitle.text = resources.getString(R.string.analogous)
        analogousColorBase.setColor(selectedColor)
        val analogousColors = selectedColor.analogous()
        analogousColor1.setColor(analogousColors.first)
        analogousColor2.setColor(analogousColors.second)

        analogousColorBaseHex.text = hexColor
        analogousColor1Hex.text = analogousColors.first.toHex()
        analogousColor2Hex.text = analogousColors.second.toHex()
    }

    private fun generateTriadic(selectedColor: Int) {
        val hexColor = selectedColor.toHex()
        triadicColorsTitle.text = resources.getString(R.string.triadic)
        triadicColorBase.setColor(selectedColor)
        val triadicColors = selectedColor.triadic()
        triadicColor1.setColor(triadicColors.first)
        triadicColor2.setColor(triadicColors.second)

        triadicColorBaseHex.text = hexColor
        triadicColor1Hex.text = triadicColors.first.toHex()
        triadicColor2Hex.text = triadicColors.second.toHex()
    }

    private fun generateTetradic(selectedColor: Int) {
        val hexColor = selectedColor.toHex()
        tetradicColorsTitle.text = resources.getString(R.string.tetradic)
        tetradicColorBase.setColor(selectedColor)
        val tetradicColors = selectedColor.tetradic()
        tetradicColor1.setColor(tetradicColors.first)
        tetradicColor2.setColor(tetradicColors.second)
        tetradicColor3.setColor(tetradicColors.third)

        tetradicColorBaseHex.text = hexColor
        tetradicColor1Hex.text = tetradicColors.first.toHex()
        tetradicColor2Hex.text = tetradicColors.second.toHex()
        tetradicColor3Hex.text = tetradicColors.third.toHex()
    }
}
