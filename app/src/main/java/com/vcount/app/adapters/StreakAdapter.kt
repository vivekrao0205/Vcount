package com.vcount.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vcount.app.R
import com.vcount.app.models.StreakModel

class StreakAdapter(
    private val list: ArrayList<StreakModel>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<StreakAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvCount: TextView = itemView.findViewById(R.id.tvCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_streak_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        circleclip

        holder.tvTitle.text = item.title
        holder.tvCount.text = item.count.toString()

        // 🔥 Count animation
        holder.tvCount.scaleX = 0.8f
        holder.tvCount.scaleY = 0.8f

        holder.tvCount.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(200)
            .start()

        // 🔥 Card click animation
        holder.itemView.setOnClickListener {
            holder.itemView.animate()
                .scaleX(0.96f)
                .scaleY(0.96f)
                .setDuration(100)
                .withEndAction {
                    holder.itemView.scaleX = 1f
                    holder.itemView.scaleY = 1f
                    onClick(position)
                }
        }
    }

    override fun getItemCount(): Int = list.size
}
