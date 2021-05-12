package com.example.movies_viper.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movies_viper.api.MoviesRepository.IMAGE_BASE_URL
import com.example.movies_viper.databinding.ItemMovieBinding
import com.example.movies_viper.model.Movie

/**
 *  Movie adapter to set movies to recyclerview
 */
class MovieListAdapter(
    private var movies: MutableList<Movie>,
    private val listener: MovieListener,
) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    lateinit var context: Context;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context;
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imagePath = IMAGE_BASE_URL + movies.get(position).posterPath;
        Glide.with(context)
            .load(imagePath)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(holder.binding.itemMoviePoster)
        holder.itemView.setOnClickListener { listener.onItemClick(movies[position]) }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    interface MovieListener {
        fun onItemClick(movie: Movie)
    }

    /**
     * Function to append movies to last index of the list
     */
    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }
}