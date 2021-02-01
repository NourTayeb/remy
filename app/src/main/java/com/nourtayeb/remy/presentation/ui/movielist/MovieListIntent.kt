package com.nourtayeb.remy.presentation.ui.movielist

import com.nourtayeb.remy.PopularQuery


sealed class MovieListUiAction {
    data class LoadMovies (val cursor:String?): MovieListUiAction()

}
sealed class MovieListUiState {
    data class MoviesLoaded(val data: PopularQuery.Movies): MovieListUiState()
    object Loading : MovieListUiState()
    data class Failed(val status: String? = null): MovieListUiState()
    data class Exception(val exception: Throwable? = null): MovieListUiState()}