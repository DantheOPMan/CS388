package com.example.bitfitpart1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryAdapter(
    private val context: Context,
    private val entries: List<EntryEntity>
) : RecyclerView.Adapter<EntryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = entries[position]
        holder.bind(entry)
    }

    override fun getItemCount() = entries.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val foodNameTextView = itemView.findViewById<TextView>(R.id.foodNameTextView)
        private val caloriesTextView = itemView.findViewById<TextView>(R.id.caloriesTextView)

        fun bind(entry: EntryEntity) {
            foodNameTextView.text = entry.foodName
            caloriesTextView.text = "${entry.calories} kcal"
        }
    }
}
