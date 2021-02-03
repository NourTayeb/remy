package com.nourtayeb.remy.data.repository

import com.nourtayeb.remy.PopularQuery
import com.nourtayeb.remy.domain.entity.MovieList

interface MoviesRepository {

    suspend fun getMovies(cursor:String?): MovieList?
}