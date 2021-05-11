package com.example.movies_viper.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie WHERE category IN (:category)")
    fun loadByCategory(category: String): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(movies: List<MovieEntity>)

    @Delete
    fun delete(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieEntity: MovieEntity)

    @Query("select * from movie")
    fun getAll(): LiveData<List<MovieEntity>>

    @Update
    fun update(movieEntity: MovieEntity)

}