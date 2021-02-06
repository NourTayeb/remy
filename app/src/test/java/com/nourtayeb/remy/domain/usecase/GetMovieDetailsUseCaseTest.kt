package com.nourtayeb.remy.domain.usecase

import com.nourtayeb.remy.data.repository.MoviesRepository
import com.nourtayeb.remy.presentation.common.movieMapped
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetMovieDetailsUseCaseTest {

    lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @RelaxedMockK
    lateinit var moviesRepository: MoviesRepository

    @Before
    fun init(){
        MockKAnnotations.init(this)
        getMovieDetailsUseCase= GetMovieDetailsUseCase(moviesRepository)
    }
    @Test
    fun `buildUseCase returns repository getMovieDetails`() = runBlockingTest{
        val movieId = 1
        coEvery { moviesRepository.getMovieDetails(movieId) } returns movieMapped
        val returned = getMovieDetailsUseCase.buildUseCase(movieId)
        Assert.assertEquals(returned, movieMapped)
    }
}