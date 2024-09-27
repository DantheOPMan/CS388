package com.example.flixsterpart1

import MovieRecyclerViewAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        recyclerView = view.findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Fetch movies from API
        fetchMovies()

        return view
    }

    private fun fetchMovies() {
        val movieApiService = RetrofitInstance.apiService

        movieApiService.getNowPlayingMovies("a07e22bc18f5cb106bfe4cc1f83ad8ed")
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    if (response.isSuccessful) {
                        val movies = response.body()?.movies ?: emptyList()

                        // Log the number of movies received
                        Log.d("MovieListFragment", "Fetched ${movies.size} movies")

                        // Log each movie's title, overview, and poster path
                        for (movie in movies) {
                            Log.d("MovieListFragment", "Title: ${movie.title}")
                            Log.d("MovieListFragment", "Overview: ${movie.overview}")
                            Log.d("MovieListFragment", "Poster Path: ${movie.posterPath}")
                        }

                        recyclerView.adapter = MovieRecyclerViewAdapter(movies)
                    } else {
                        Log.e("MovieListFragment", "Failed to fetch movies: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    // Log the error
                    Log.e("MovieListFragment", "Error fetching movies", t)
                }
            })
    }
}
