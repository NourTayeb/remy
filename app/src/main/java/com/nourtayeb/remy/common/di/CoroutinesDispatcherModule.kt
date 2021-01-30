package com.nourtayeb.remy.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object CoroutinesDispatcherModule {

    @Provides
    @Singleton
    fun CoroutineDispatcher() :CoroutineDispatcher = Dispatchers.IO
}