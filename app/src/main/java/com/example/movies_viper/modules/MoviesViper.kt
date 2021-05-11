package com.example.movies_viper.modules

import android.app.Application
import com.example.movies_viper.database.MovieDatabase

lateinit var movieDb: MovieDatabase

class MoviesViper : Application() {

    companion object {
        lateinit var INSTANCE: MoviesViper
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        movieDb = MovieDatabase.getInstance(this)
        INSTANCE = this
    }
}