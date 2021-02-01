package com.nourtayeb.remy.data.repository

import com.nourtayeb.remy.PopularQuery

interface MoviesRepository {

    suspend fun getMovies(cursor:String?): PopularQuery.Movies?
}