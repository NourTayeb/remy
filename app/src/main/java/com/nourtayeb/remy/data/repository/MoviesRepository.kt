package com.nourtayeb.remy.data.repository

import com.nourtayeb.remy.PopularQuery
import com.nourtayeb.remy.domain.entity.Movie
import com.nourtayeb.remy.domain.entity.MovieList

interface MoviesRepository {

    suspend fun getMovies(cursor:String?): MovieList?
    suspend fun getMovieDetails(id:Int): Movie?
}