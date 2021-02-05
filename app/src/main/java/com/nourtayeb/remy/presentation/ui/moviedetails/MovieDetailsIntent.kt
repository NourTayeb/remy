package com.nourtayeb.remy.presentation.ui.moviedetails

import com.nourtayeb.remy.PopularQuery
import com.nourtayeb.remy.domain.entity.Movie
import com.nourtayeb.remy.domain.entity.MovieList


sealed class MovieDetailsUiAction {
    data class LoadMovieDetails (val id:Int): MovieDetailsUiAction()

}
sealed class MovieDetailsUiState {
    data class MovieLoaded(val id: Int,
                           val title: String,
                           val rating: Double,
                           val poster: String?,
                           val releaseDate: String ,
                           val details: String): MovieDetailsUiState()
    object Loading : MovieDetailsUiState()
    data class Failed(val status: String? = null): MovieDetailsUiState()
    data class Exception(val exception: Throwable? = null): MovieDetailsUiState()}