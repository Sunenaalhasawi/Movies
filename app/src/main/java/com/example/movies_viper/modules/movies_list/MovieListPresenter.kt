package com.example.movies_viper.modules.movies_list

import android.util.Log
import androidx.lifecycle.Observer
import com.example.movies_viper.model.Movie

/**
 * The presenter class accepts actions from activities and give data upon request
 */

class MovieListPresenter(
    private val router: MovieListContract.Router,
    private val interactor: MovieListInteractor
) : MovieListContract.Presenter {

    private var view: MovieListContract.View? = null

    /**
     * @param view
     * Function to set Activity view to the presenter
     */
    override fun bindView(view: MovieListContract.View) {
        this.view = view
    }

    /**
     * @param initialFetching
     * @param popularmoviePage
     * Function to load popular movies from server. Accepts page number to support pagination
     */
    override fun onPopularMovieViewCreated(popularmoviePage: Int, initialFetching: Boolean) {
        if (initialFetching)
            view?.showLoading()
        interactor.getPopularMovies(
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError, popularmoviePage
        )
    }

    /**
     * @param initialFetching
     * @param topRatedMoviePage
     * Function to load top rated movies from server. Accepts page number to support pagination
     */
    override fun onTopRatedViewCreated(topRatedMoviePage: Int, initialFetching: Boolean) {
        if (initialFetching)
            view?.showLoading()
        interactor.getTopRatedMovies(
            onSuccess = ::onTopRatedMoviesFetched,
            onError = ::onError, topRatedMoviePage
        )
    }

    /**
     * @param initialFetching
     * @param upcomingMoviePage
     * Function to load upcoming movies from server. Accepts page number to support pagination
     */
    override fun onUpcomingViewCreated(upcomingMoviePage: Int, initialFetching: Boolean) {
        if (initialFetching)
            view?.showLoading()
        interactor.getUpcomingMovies(
            onSuccess = ::onUpcomingMoviesFetched,
            onError = ::onError, upcomingMoviePage
        )
    }

    /**
     * @param movie
     * Function to listen to recyclerview item click
     */
    override fun onItemClicked(movie: Movie) {
        router.openFullMovie(movie)
    }

    /**
     * @param movies
     * Function to get the popular movies data from api response
     * It invokes when api returns success
     */
    private fun onPopularMoviesFetched(movies: List<Movie>) {
        Log.d("MovieList", "Movies: $movies")
        view?.hideLoading()
        view?.publishPopularMoviesData(movies)
    }

    /**
     * @param movies
     * Function to get the top rated movies data from api response
     * It invokes when api returns success
     */
    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        Log.d("MovieList", "Movies: $movies")
        view?.hideLoading()
        view?.publishTopRatedMoviesData(movies)
    }

    /**
     * @param movies
     * Function to get the upcoming movies data from api response
     * It invokes when api returns success
     */
    private fun onUpcomingMoviesFetched(movies: List<Movie>) {
        Log.d("MovieList", "Movies: $movies")
        view?.hideLoading()
        view?.publishUpcomingMoviesData(movies)
    }

    /**
     * Function to get data from local database and notify the view that data has been fetched
     */
    override fun onMovieViewCreated(activity: MoviesListActivity, category: String) {
        view?.showLoading()
        interactor.getSavedMovies(category).observe(activity, Observer { movieEntities ->
            movieEntities?.let {
                var moviesListItems: MutableList<Movie> = mutableListOf()
                for (i in movieEntities.indices) {

                    var movieItem: Movie = Movie(
                        movieEntities[i].adult!!,
                        movieEntities[i].id!!,
                        movieEntities[i].originalLanguage!!,
                        movieEntities[i].originalTitle!!,
                        movieEntities[i].popularity!!,
                        movieEntities[i].video!!,
                        movieEntities[i].voteCount!!,
                        movieEntities[i].title!!,
                        movieEntities[i].overview!!,
                        movieEntities[i].posterPath!!,
                        movieEntities[i].backdropPath!!,
                        movieEntities[i].rating!!,
                        movieEntities[i].releaseDate!!
                    )
                    moviesListItems.add(movieItem)
                }
                if (category?.equals("popular"))
                    view?.fetchMoviesFromLocal(moviesListItems, "popular")
                else if (category?.equals("top rated"))
                    view?.fetchMoviesFromLocal(moviesListItems, "top rated")
                else if (category?.equals("upcoming"))
                    view?.fetchMoviesFromLocal(moviesListItems, "upcoming")
            }
        })
    }

    /**
     * Function to show error when api failed to load data
     */
    private fun onError() {
        view?.hideLoading()
        view?.showMessage("Error in loading data")
    }

    override fun onBackClicked() {
        router.finish()
    }
}