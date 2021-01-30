package com.nourtayeb.remy.common.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.nourtayeb.remy.common.SERVER_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

@Module
@InstallIn(ApplicationComponent::class)
object NetworkingModule {


    @Provides
    fun provideAuthIntercepter(@ApplicationContext context: Context):AuthorizationInterceptor{
        return AuthorizationInterceptor(context)
    }
    @Provides
    fun provideOkHttpClient(authorizationInterceptor: AuthorizationInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authorizationInterceptor)
            .build()
    }


    @Provides
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
                .serverUrl(SERVER_URL)
            .okHttpClient(okHttpClient)
            .build()
    }
}
 class AuthorizationInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .build()

        return chain.proceed(request)
    }
}