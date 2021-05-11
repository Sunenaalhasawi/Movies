package com.example.movies_viper.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false) val id: Long?,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "adult") val adult: Boolean?,
    @ColumnInfo(name = "original_language") val originalLanguage: String?,
    @ColumnInfo(name = "original_title") val originalTitle: String?,
    @ColumnInfo(name = "popularity") val popularity: Float?,
    @ColumnInfo(name = "video") val video: Boolean?,
    @ColumnInfo(name = "vote_count") val voteCount: Long?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "vote_average") val rating: Float?,
    @ColumnInfo(name = "release_date") val releaseDate: String?
)