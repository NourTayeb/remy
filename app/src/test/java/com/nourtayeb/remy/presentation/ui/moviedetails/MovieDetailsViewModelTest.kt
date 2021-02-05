package com.nourtayeb.remy.presentation.ui.moviedetails

import com.nourtayeb.remy.domain.usecase.GetMovieDetailsUseCase
import com.nourtayeb.remy.domain.usecase.GetPopularMoviesUseCase
import com.nourtayeb.remy.presentation.common.base.ViewModelBaseTest
import com.nourtayeb.remy.presentation.common.dummyMovie
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
    lateinit var viewModel: MovieDetailsViewModel

    @RelaxedMockK
    lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Before
    fun init() {
        viewModel = MovieDetailsViewModel(testDispatcher, getMovieDetailsUseCase)
    }

    @Test
    fun `null response of getMovieDetailsUseCase emits Loading then Failed`() =
        runBlockingTest {
            coEvery { getMovieDetailsUseCase.buildUseCase(dummyMovie.id) } returns null
            val list = mutableListOf<MovieDetailsUiState>()
            val flow = flow {
                emit(MovieDetailsUiAction.LoadMovieDetails(dummyMovie.id))
            }
            viewModel.reduce(flow).collect {
                list.add(it)
            }
            val expectedResult = listOf(
                MovieDetailsUiState.Loading,
                MovieDetailsUiState.Failed()
            )
            Assert.assertEquals(expectedResult, list)
        }

    @Test
    fun `nonempty response of getMovieDetailsUseCase emits Loading then MoviesLoaded`() =
        runBlockingTest {
            val movieLoadedState = MovieDetailsUiState.MovieLoaded(
                dummyMovie.id,
                dummyMovie.title,
                dummyMovie.rating,
                dummyMovie.poster,
                dummyMovie.releaseDate,
                dummyMovie.details
            )
            coEvery { getMovieDetailsUseCase.buildUseCase(dummyMovie.id) } returns dummyMovie
            val list = mutableListOf<MovieDetailsUiState>()
            val flow = flow {
                emit(MovieDetailsUiAction.LoadMovieDetails(dummyMovie.id))
            }
            viewModel.reduce(flow).collect {
                list.add(it)
            }
            val expectedResult = listOf(
                MovieDetailsUiState.Loading,
                movieLoadedState
            )
            Assert.assertEquals(expectedResult, list)
        }

    @Test
    fun `Exception emits Loading then ExceptionUiState`() =
        runBlockingTest {
            val throwable = Throwable("error")
            coEvery { getMovieDetailsUseCase.buildUseCase(dummyMovie.id) } throws throwable
            val list = mutableListOf<MovieDetailsUiState>()
            val flow = flow {
                emit(MovieDetailsUiAction.LoadMovieDetails(dummyMovie.id))
            }
            viewModel.reduce(flow).collect {
                list.add(it)
            }
            val expectedResult = listOf(
                MovieDetailsUiState.Loading,
                MovieDetailsUiState.Exception(throwable)
            )
            Assert.assertEquals(expectedResult.size, list.size)
            Assert.assertEquals(expectedResult[0], list[0])
            Assert.assertTrue(expectedResult[1].sameError(list[1]))
        }
}

fun MovieDetailsUiState.sameError(other: MovieDetailsUiState): Boolean {
    return this is MovieDetailsUiState.Exception && other is MovieDetailsUiState.Exception && toString().equals(
        other.toString()
    )
}