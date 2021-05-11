package com.example.movies_viper.modules.movie_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movies_viper.*
import com.example.movies_viper.api.MoviesRepository.IMAGE_BASE_URL
import com.example.movies_viper.databinding.ActivityMovieDetailBinding
import com.example.movies_viper.model.Movie


/**
 * The MovieDetailsActivity presents a detailed view of each movie to the user
 */
class MovieDetailsActivity : AppCompatActivity(), DetailContract.View {

    lateinit var movieDetailBinding: ActivityMovieDetailBinding
    lateinit var presenter: DetailPresenter

    companion object {
        private const val MOVIE = "${BuildConfig.APPLICATION_ID}_MOVIE"

        fun launch(context: Context, data: Movie) {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE, data)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Data binding to layout
        movieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        initView()
        // Initializing presenter
        presenter = DetailPresenter(DetailRouter(this))
        presenter.bindView(this)
        if (intent.hasExtra(MOVIE)) {
            var movie = intent.getParcelableExtra<Movie>(MOVIE)
            movie?.let { presenter.onViewCreated(it) }
        } else {
            presenter.onEmptyData(R.string.empty_data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    /**
     * Function to set movie values to views
     */
    override fun publishData(movie: Movie) {
        Glide.with(this)
            .load(IMAGE_BASE_URL + movie.backdropPath)
            .transform(CenterCrop())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(movieDetailBinding.movieBackdrop)

        Glide.with(this)
            .load(IMAGE_BASE_URL + movie.posterPath)
            .transform(CenterCrop())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(movieDetailBinding.moviePoster)

        movieDetailBinding.movieTitle.text = movie.title
        movieDetailBinding.movieRating.rating = movie.rating / 2
        movieDetailBinding.movieReleaseDate.text = movie.releaseDate
        movieDetailBinding.movieOverview.text = movie.overview
    }

    /**
     * Function to show toast in case of any error
     */
    override fun showMessage(msg: Int) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * Function to handle toolbar back
     */
    private fun initView() {
        movieDetailBinding.toolbarView.toolbar.setNavigationOnClickListener { presenter.onBackClicked() }
    }


}
