package com.nourtayeb.remy.domain.usecase

import com.nourtayeb.remy.PopularQuery
import com.nourtayeb.remy.data.repository.MoviesRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend fun buildUseCase(cursor:String?): PopularQuery.Movies? {
        return moviesRepository.getMovies(cursor)
    }

}