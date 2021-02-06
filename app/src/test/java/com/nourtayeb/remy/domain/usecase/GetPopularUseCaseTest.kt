package com.nourtayeb.remy.domain.usecase

import com.nourtayeb.remy.data.repository.MoviesRepository
import com.nourtayeb.remy.presentation.common.movieList
import com.nourtayeb.remy.presentation.common.movieMapped
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetPopularUseCaseTest {

    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @RelaxedMockK
    lateinit var moviesRepository: MoviesRepository

    @Before
    fun init(){
        MockKAnnotations.init(this)
        getPopularMoviesUseCase= GetPopularMoviesUseCase(moviesRepository)
    }
    @Test
    fun `buildUseCase returns repository getMovies`() = runBlockingTest{
        coEvery { moviesRepository.getMovies(null) } returns movieList
        val returned = getPopularMoviesUseCase.buildUseCase(null)
        Assert.assertEquals(returned,movieList)
    }
}