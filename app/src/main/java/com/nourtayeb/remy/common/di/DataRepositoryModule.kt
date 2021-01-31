package com.nourtayeb.remy.common.di

import android.content.SharedPreferences
import com.apollographql.apollo.ApolloClient
import com.nourtayeb.remy.data.repository.MoviesRepository
import com.nourtayeb.remy.data.repository.MoviesRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object DataRepositoryModule {
    @Provides
    fun provideMoviesRepo(apolloClient: ApolloClient):MoviesRepository {
        return MoviesRepositoryImp(apolloClient)
    }


}