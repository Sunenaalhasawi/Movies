package com.example.movies_viper.api

import com.example.movies_viper.model.GetMoviesResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepository {

    private val apiInterface: RetrofitApiInterface
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original/"
    const val API_KEY = "bf31b3ccafccaa8938689ba6076b02c5";

    // Initializing retrofit api client
    init {
        val httpClient = OkHttpClient.Builder()
        val client = httpClient.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiInterface = retrofit.create(RetrofitApiInterface::class.java)
    }

    /**
     * Retrofit api call to fetch popular movies
     */
    fun getPopularMovies(page: Int): Call<GetMoviesResponse> =
        apiInterface.getPopularMovies(page = page)

    /**
     * Retrofit api call to fetch top rated movies
     */
    fun getTopRatedMovies(page: Int): Call<GetMoviesResponse> =
        apiInterface.getTopRatedMovies(page = page)

    /**
     * Retrofit api call to fetch upcoming movies
     */
    fun getUpcomingMovies(page: Int): Call<GetMoviesResponse> =
        apiInterface.getUpcomingMovies(page = page)
}