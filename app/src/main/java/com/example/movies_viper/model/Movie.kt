package com.example.movies_viper.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("adult") @Expose val adult: Boolean,
    @SerializedName("id") @Expose val id: Long,
    @SerializedName("original_language") @Expose val originalLanguage: String,
    @SerializedName("original_title") @Expose val originalTitle: String,
    @SerializedName("popularity") @Expose val popularity: Float,
    @SerializedName("video") @Expose val video: Boolean,
    @SerializedName("vote_count") @Expose val voteCount: Long,
    @SerializedName("title") @Expose val title: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("poster_path") @Expose val posterPath: String,
    @SerializedName("backdrop_path") @Expose val backdropPath: String,
    @SerializedName("vote_average") @Expose val rating: Float,
    @SerializedName("release_date") @Expose val releaseDate: String
) : Parcelable