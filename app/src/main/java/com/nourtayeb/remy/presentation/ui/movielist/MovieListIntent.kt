package com.nourtayeb.remy.presentation.ui.movielist

import com.nourtayeb.remy.PopularQuery
import com.nourtayeb.remy.domain.entity.MovieList


sealed class MovieListUiAction {
    data class LoadMovies (val cursor:String?= null): MovieListUiAction()

}
sealed class MovieListUiState {
    data class MoviesLoaded(val data: MovieList): MovieListUiState()
    object Loading : MovieListUiState()
    data class Failed(val status: String? = null): MovieListUiState()
    data class Exception(val exception: Throwable? = null): MovieListUiState()}