package com.nourtayeb.remy.presentation.ui.moviedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nourtayeb.remy.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class MovieDetailsViewModel @ViewModelInject constructor(
        var dispatcher: CoroutineDispatcher,
        private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    lateinit var state: LiveData<MovieDetailsUiState>

    fun reduce(flow2: Flow<MovieDetailsUiAction>): Flow<MovieDetailsUiState> {
        val flow = flow2.distinctUntilChangedBy { it }.flatMapMerge {
            when (it) {
                is MovieDetailsUiAction.LoadMovieDetails -> flow {
                    val moviesResult = getMovieDetailsUseCase.buildUseCase(it.id)
                    if (moviesResult == null) {
                        emit(MovieDetailsUiState.Failed())
                    } else {
                        emit(MovieDetailsUiState.MovieLoaded(moviesResult.id,moviesResult.title,moviesResult.rating,moviesResult.poster,
                        moviesResult.releaseDate,moviesResult.details))
                    }
                }.onStart { emit(MovieDetailsUiState.Loading) }
                else -> throw IllegalArgumentException("Not supported Action")
            }
        }.catch { e -> emit(MovieDetailsUiState.Exception(e)) }

        state = flow.asLiveData(dispatcher)

        return flow
    }


}