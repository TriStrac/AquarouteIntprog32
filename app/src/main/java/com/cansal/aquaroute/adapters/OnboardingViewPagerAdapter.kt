package com.cansal.aquaroute.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cansal.aquaroute.R

class OnboardingViewPagerAdapter(private val context: Context) : RecyclerView.Adapter<OnboardingViewPagerAdapter.OnboardingViewHolder>() {

    private val images = intArrayOf(
        R.drawable.page1,
        R.drawable.page2,
        R.drawable.page3
    )

    private val headings = intArrayOf(
        R.string.header1,
        R.string.header2,
        R.string.header3
    )

    private val descriptions = intArrayOf(
        R.string.description1,
        R.string.description2,
        R.string.description3
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.slider_layout, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.slidetitleimage.setImageResource(images[position])
        holder.slideHeading.setText(headings[position])
        holder.slideDescription.setText(descriptions[position])
    }

    override fun getItemCount(): Int {
        return headings.size
    }

    inner class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slidetitleimage: ImageView = itemView.findViewById(R.id.imageDisplay)
        val slideHeading: TextView = itemView.findViewById(R.id.titleDisplay)
        val slideDescription: TextView = itemView.findViewById(R.id.descriptionDisplay)
    }
}
