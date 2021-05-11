package com.example.movies_viper.modules.movies_list

import com.example.movies_viper.model.Movie
import com.example.movies_viper.modules.movie_detail.MovieDetailsActivity

/**
 * The Router class helps to navigate to next screen
 */
class MovieListRouter(private val activity: MoviesListActivity) : MovieListContract.Router {

    override fun finish() {
        activity.finish()
    }

    /**
     * Function to launch MovieDetailActivity as user clicks on any movie
     */
    override fun openFullMovie(data: Movie) {
        MovieDetailsActivity.launch(activity, data)
    }
}