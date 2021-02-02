package com.nourtayeb.remy.presentation.ui.movielist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nourtayeb.remy.domain.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class MovieListViewModel @ViewModelInject constructor(
        var dispatcher: CoroutineDispatcher,
        private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    lateinit var state: LiveData<MovieListUiState>

    fun reduce(flow2: Flow<MovieListUiAction>): Flow<MovieListUiState> {
        val flow = flow2.distinctUntilChangedBy { it }.flatMapMerge {
            when (it) {
                is MovieListUiAction.LoadMovies -> flow {
                    val MoviesResult = getPopularMoviesUseCase.buildUseCase(it.cursor)
                    if (MoviesResult == null || MoviesResult.movies.isEmpty()) {
                        emit(MovieListUiState.Failed())
                    } else {
                        emit(MovieListUiState.MoviesLoaded(MoviesResult))
                    }
                }.onStart { emit(MovieListUiState.Loading) }
                else -> throw IllegalArgumentException("Not supported Action")
            }
        }.catch { e -> emit(MovieListUiState.Exception(e)) }

        state = flow.asLiveData(dispatcher)

        return flow
    }


}