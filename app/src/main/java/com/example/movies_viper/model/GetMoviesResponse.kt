package com.example.movies_viper.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("page") @Expose val page: Int,
    @SerializedName("results") @Expose val movies: List<Movie>,
    @SerializedName("total_pages") @Expose val pages: Long,
    @SerializedName("total_results") @Expose val results: Long
)