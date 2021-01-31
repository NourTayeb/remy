package com.nourtayeb.remy.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.nourtayeb.remy.PopularQuery
import kotlinx.coroutines.delay
import javax.inject.Inject

class MoviesRepositoryImp @Inject constructor(val apolloClient: ApolloClient):MoviesRepository{

    override suspend fun getMovies(): PopularQuery.Movies? {
        val responseDeffered = apolloClient.query(PopularQuery())
        val response = responseDeffered.await()
        return response.data?.movies
    }
}