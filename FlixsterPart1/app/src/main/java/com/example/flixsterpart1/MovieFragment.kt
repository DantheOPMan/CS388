package com.example.flixsterpart1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class MovieFragment : Fragment() {

    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)

        movie = arguments?.getSerializable("movie") as Movie

        // Set movie title and overview
        val titleTextView: TextView = view.findViewById(R.id.movie_title)
        val overviewTextView: TextView = view.findViewById(R.id.movie_overview)
        val movieImageView: ImageView = view.findViewById(R.id.movie_image)

        titleTextView.text = movie.title
        overviewTextView.text = movie.overview

        // Load movie poster with Glide
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/" + movie.posterPath)
            .into(movieImageView)

        return view
    }
}
