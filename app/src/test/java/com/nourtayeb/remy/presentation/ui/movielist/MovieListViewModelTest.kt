package com.nourtayeb.remy.presentation.ui.movielist

import com.nourtayeb.remy.domain.usecase.GetPopularMoviesUseCase
import com.nourtayeb.remy.presentation.common.base.ViewModelBaseTest
import com.nourtayeb.remy.presentation.common.emptyMovieList
import com.nourtayeb.remy.presentation.common.movieList
import io.mockk.InternalPlatformDsl.toStr
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class ProductListViewModelTest : ViewModelBaseTest() {
    lateinit var viewModel: MovieListViewModel

    @RelaxedMockK
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Before
    fun init() {
        viewModel = MovieListViewModel(testDispatcher, getPopularMoviesUseCase)
    }

    @Test
    fun `empty response of getPopularMoviesUseCase emits Loading then Failed`() =
        runBlockingTest {
            coEvery { getPopularMoviesUseCase.buildUseCase() } returns emptyMovieList
            val list = mutableListOf<MovieListUiState>()
            val flow = flow {
                emit(MovieListUiAction.LoadMovies())
            }
            viewModel.reduce(flow).collect {
                list.add(it)
            }
            val expectedResult = listOf(
                MovieListUiState.Loading,
                MovieListUiState.Failed()
            )
            Assert.assertEquals(expectedResult, list)
        }

    @Test
    fun `nonempty response of getPopularMoviesUseCase emits Loading then MoviesLoaded`() =
        runBlockingTest {
            coEvery { getPopularMoviesUseCase.buildUseCase() } returns movieList
            val list = mutableListOf<MovieListUiState>()
            val flow = flow {
                emit(MovieListUiAction.LoadMovies())
            }
            viewModel.reduce(flow).collect {
                list.add(it)
            }
            val expectedResult = listOf(
                MovieListUiState.Loading,
                MovieListUiState.MoviesLoaded(movieList)
            )
            Assert.assertEquals(expectedResult, list)
        }

    @Test
    fun `Exception emits Loading then ExceptionUiState`() =
        runBlockingTest {
            val throwable = Throwable("error")
            coEvery { getPopularMoviesUseCase.buildUseCase() } throws throwable
            val list = mutableListOf<MovieListUiState>()
            val flow = flow {
                emit(MovieListUiAction.LoadMovies())
            }
            viewModel.reduce(flow).collect {
                list.add(it)
            }
            val expectedResult = listOf(
                MovieListUiState.Loading,
                MovieListUiState.Exception(throwable)
            )
            Assert.assertEquals(expectedResult.size, list.size)
            Assert.assertEquals(expectedResult[0], list[0])
            Assert.assertTrue(expectedResult[1].sameError(list[1]))
        }
}

fun MovieListUiState.sameError(other: MovieListUiState): Boolean {
    return this is MovieListUiState.Exception && other is MovieListUiState.Exception && toString().equals(
        other.toString()
    )
}