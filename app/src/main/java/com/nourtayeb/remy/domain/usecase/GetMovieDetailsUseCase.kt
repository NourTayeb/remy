package com.nourtayeb.remy.domain.usecase

import com.nourtayeb.remy.PopularQuery
import com.nourtayeb.remy.data.mapper.MovieMapper
import com.nourtayeb.remy.data.repository.MoviesRepository
import com.nourtayeb.remy.domain.entity.Movie
import com.nourtayeb.remy.domain.entity.MovieList
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend fun buildUseCase(id:Int): Movie? {
        return moviesRepository.getMovieDetails(id)
    }

}