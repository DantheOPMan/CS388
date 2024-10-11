// PersonAdapter.kt
package com.example.flixsterpart2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val PERSON_EXTRA = "PERSON_EXTRA"

class PersonAdapter(private val context: Context, private val persons: List<Person>) :
    RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = persons[position]
        holder.bind(person)
    }

    override fun getItemCount() = persons.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val personImageView: ImageView = itemView.findViewById(R.id.personImageView)
        private val personNameTextView: TextView = itemView.findViewById(R.id.personNameTextView)

        private lateinit var person: Person

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(person: Person) {
            this.person = person
            personNameTextView.text = person.name

            // Load image with Glide
            Glide.with(context)
                .load(person.profileImageUrl)
                .placeholder(R.drawable.placeholder_image) // Ensure you have this image in drawable
                .error(R.drawable.error_image) // Placeholder for error
                .into(personImageView)
        }

        override fun onClick(v: View?) {
            // Navigate to DetailActivity with the selected person
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(PERSON_EXTRA, person)
            context.startActivity(intent)
        }
    }
}
