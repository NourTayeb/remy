package com.nourtayeb.remy.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.nourtayeb.remy.DetailsQuery
import com.nourtayeb.remy.PopularQuery
import com.nourtayeb.remy.data.mapper.MovieMapper
import com.nourtayeb.remy.domain.entity.Movie
import com.nourtayeb.remy.domain.entity.MovieList
import kotlinx.coroutines.delay
import javax.inject.Inject

class MoviesRepositoryImp @Inject constructor(val apolloClient: ApolloClient,val movieMapper: MovieMapper):MoviesRepository{

    override suspend fun getMovies(cursor:String?): MovieList? {
        val responseDeffered = apolloClient.query(PopularQuery(cursor = Input.fromNullable(cursor)))
        val response = responseDeffered.await()
        return movieMapper.remoteToEntity(response.data?.movies)
    }
     override suspend fun getMovieDetails(id:Int): Movie? {
        val responseDeffered = apolloClient.query(DetailsQuery(id))
        val response = responseDeffered.await()
        return movieMapper.remoteToEntity(response.data?.movies!!.movie)
    }
}