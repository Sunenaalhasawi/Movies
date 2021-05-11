package com.example.movies_viper.modules.movies_list

import androidx.lifecycle.LiveData
import com.example.movies_viper.database.MovieEntity
import com.example.movies_viper.model.Movie

/**
 * This is the MovieListContract class. Here we register all the methods that we are using in five levels of Viper architecture pattern
 */
interface MovieListContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun fetchMoviesFromLocal(data: List<Movie>, category: String)
        fun publishPopularMoviesData(data: List<Movie>)
        fun publishTopRatedMoviesData(data: List<Movie>)
        fun publishUpcomingMoviesData(data: List<Movie>)
        fun showMessage(msg: String)
    }

    interface Presenter {
        fun bindView(view: View)
        fun onMovieViewCreated(activity: MoviesListActivity, category: String)
        fun onPopularMovieViewCreated(page: Int, initialFetching: Boolean)
        fun onTopRatedViewCreated(page: Int, initialFetching: Boolean)
        fun onUpcomingViewCreated(page: Int, initialFetching: Boolean)
        fun onItemClicked(movie: Movie)
        fun onBackClicked()
    }

    interface Interactor {
        fun getPopularMovies(onSuccess: (List<Movie>) -> Unit, onError: () -> Unit, page: Int)
        fun getTopRatedMovies(onSuccess: (List<Movie>) -> Unit, onError: () -> Unit, page: Int)
        fun getUpcomingMovies(onSuccess: (List<Movie>) -> Unit, onError: () -> Unit, page: Int)
        fun getSavedMovies(category: String): LiveData<List<MovieEntity>>
        fun saveMoviesToOfflineCache(entity: MovieEntity)
    }

    interface Router {
        fun finish()
        fun openFullMovie(movie: Movie)
    }
}