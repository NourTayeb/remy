package com.nourtayeb.remy.common.di

import com.nourtayeb.remy.data.mapper.MovieMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun movieMapper() = MovieMapper()


}