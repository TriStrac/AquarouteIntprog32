package com.cansal.aquaroute.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cansal.aquaroute.R

class RegisterChoiceViewPagerAdapter(private val context: Context) : RecyclerView.Adapter<RegisterChoiceViewPagerAdapter.ChoiceViewHolder>() {

    private val images = intArrayOf(
        R.drawable.customer_fade,
        R.drawable.owner_fade
    )

    private val headings = intArrayOf(
        R.string.header2_1,
        R.string.header2_2
    )

    private val descriptions = intArrayOf(
        R.string.description2_1,
        R.string.description2_2,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.choice_slider_layout, parent, false)
        return ChoiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChoiceViewHolder, position: Int) {
        holder.choiceImage.setImageResource(images[position])
        holder.choiceTitle.setText(headings[position])
        holder.choiceDescription.setText(descriptions[position])
    }

    override fun getItemCount(): Int {
        return headings.size
    }

    inner class ChoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val choiceImage: ImageView = itemView.findViewById(R.id.imageChoiceDisplay)
        val choiceTitle: TextView = itemView.findViewById(R.id.titleChoiceDisplay)
        val choiceDescription: TextView = itemView.findViewById(R.id.descriptionChoiceDisplay)
    }
}