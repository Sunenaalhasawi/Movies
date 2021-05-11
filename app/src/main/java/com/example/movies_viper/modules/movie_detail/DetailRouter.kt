package com.example.movies_viper

import com.example.movies_viper.modules.movie_detail.MovieDetailsActivity

/**
 * The Router class helps to navigate
 */
class DetailRouter(private val activity: MovieDetailsActivity) : DetailContract.Router {

    override fun finish() {
        activity.finish()
    }
}