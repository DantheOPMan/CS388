import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.flixsterpart1.Movie
import com.example.flixsterpart1.R

class MovieRecyclerViewAdapter(
    private val movies: List<Movie>
) : RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.movieTitle.text = movie.title
        holder.movieOverview.text = movie.overview

        // Load movie poster with Glide and use placeholder/error images
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/" + movie.posterPath)
            .apply(RequestOptions()
                .placeholder(R.drawable.placeholder_image)  // Placeholder image while loading
                .error(R.drawable.error_image))  // Error image if the load fails
            .into(holder.movieImage)
    }

    override fun getItemCount(): Int = movies.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieTitle: TextView = view.findViewById(R.id.movie_title)
        val movieOverview: TextView = view.findViewById(R.id.movie_overview)
        val movieImage: ImageView = view.findViewById(R.id.movie_image)
    }
}
