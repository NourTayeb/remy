package com.nourtayeb.remy.data.mapper

import com.nourtayeb.remy.PopularQuery
import com.nourtayeb.remy.domain.entity.Movie
import com.nourtayeb.remy.domain.entity.MovieList
import javax.inject.Inject

class MovieMapper @Inject constructor() {
    fun remoteToEntity( remoteMovies: PopularQuery.Movies?):MovieList?{
        try {
            val movies = remoteMovies!!.popular.edges?.map {
                Movie(
                    it!!.cursor,
                    it.node!!.id,
                    it.node.title,
                    it.node.rating,
                    it.node.poster.toString()
                )
            }
            return MovieList(movies!!, remoteMovies.popular.pageInfo.hasNextPage)
        }catch (e:Exception){
            return null
        }

    }
}