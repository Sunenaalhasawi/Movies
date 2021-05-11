package com.example.movies_viper.modules.movies_list

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.movies_viper.api.MoviesRepository
import com.example.movies_viper.database.MovieEntity
import com.example.movies_viper.model.GetMoviesResponse
import com.example.movies_viper.model.Movie
import com.example.movies_viper.modules.movieDb
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

/**
 * This interactor handles data fetching from server and local cache
 */
class MovieListInteractor() : MovieListContract.Interactor {

    // Intiailzing Room database object
    private val movieDao = movieDb.movieDao()

    /**
     * @param category
     * @return list of movies
     * Function to fetch movies from database if category is given
     */
    override fun getSavedMovies(category: String): LiveData<List<MovieEntity>> {
        return movieDao.loadByCategory(category)
    }

    /**
     * Function to save each movie to database table
     */
    override fun saveMoviesToOfflineCache(entity: MovieEntity) {
        thread { movieDao.insert(entity) }
    }

    /**
     * Function to fetch popular movies from server using Retrofit api
     */
    override fun getPopularMovies(
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit, page: Int
    ) {
        MoviesRepository.getPopularMovies(page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            Log.d("Repository", "Movies: ${responseBody.movies}")
                            onSuccess.invoke(responseBody.movies)
                            saveMoviesToLocal(responseBody.movies, "popular")

                        } else {
                            Log.d("Repository", "Failed to get response")
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                    onError.invoke()
                }
            })
    }

    /**
     * Function to fetch top rated movies from server using Retrofit api
     */
    override fun getTopRatedMovies(
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit, page: Int
    ) {
        MoviesRepository.getTopRatedMovies(page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            Log.d("Repository", "Movies: ${responseBody.movies}")
                            onSuccess.invoke(responseBody.movies)
                            saveMoviesToLocal(responseBody.movies, "top rated")
                        } else {
                            Log.d("Repository", "Failed to get response")
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                    onError.invoke()
                }
            })
    }

    /**
     * Function to fetch upcoming movies from server using Retrofit api
     */
    override fun getUpcomingMovies(
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit, page: Int
    ) {
        MoviesRepository.getUpcomingMovies(page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            Log.d("Repository", "Movies: ${responseBody.movies}")
                            onSuccess.invoke(responseBody.movies)
                            saveMoviesToLocal(responseBody.movies, "upcoming")
                        } else {
                            Log.d("Repository", "Failed to get response")
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                    onError.invoke()
                }
            })
    }

    /**
     * Function to save movies fetched from server to database for offline availability
     */
    fun saveMoviesToLocal(movies: List<Movie>, category: String) {
        for (i in movies.indices) {
            saveMoviesToOfflineCache(
                MovieEntity(
                    movies[i].id,
                    category,
                    movies[i].adult,
                    movies[i].originalLanguage,
                    movies[i].originalTitle,
                    movies[i].popularity,
                    movies[i].video,
                    movies[i].voteCount,
                    movies[i].title,
                    movies[i].overview,
                    movies[i].posterPath,
                    movies[i].backdropPath,
                    movies[i].rating,
                    movies[i].releaseDate
                )
            )

        }


    }

}