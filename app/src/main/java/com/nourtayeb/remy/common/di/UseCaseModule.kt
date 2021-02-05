package com.nourtayeb.remy.common.di

import com.nourtayeb.remy.data.repository.MoviesRepository
import com.nourtayeb.remy.domain.usecase.GetMovieDetailsUseCase
import com.nourtayeb.remy.domain.usecase.GetPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoadPopularMoviesUsecase(moviesRepository: MoviesRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(moviesRepository)
    }
    @Provides
    fun provideGetMovieDetailsUseCase(moviesRepository: MoviesRepository): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(moviesRepository)
    }

}