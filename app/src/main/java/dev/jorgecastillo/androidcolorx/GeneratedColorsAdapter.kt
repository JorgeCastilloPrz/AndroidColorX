package dev.jorgecastillo.androidcolorx

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.jorgecastillo.androidcolorx.library.asHex

class GeneratedColorsAdapter : RecyclerView.Adapter<GeneratedColorsAdapter.ViewHolder>() {

    var colors: List<Int> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_generated_color_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = colors.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(colors[position], position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(color: Int, position: Int) {
            val colorSquare = itemView.findViewById<RoundedCornersColor>(R.id.colorSquare)
            colorSquare.setColor(color)

            val colorText = itemView.findViewById<TextView>(R.id.colorText)
            colorText.text = color.asHex().toString()
        }
    }
}
