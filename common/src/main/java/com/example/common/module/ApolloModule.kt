package com.example.common.module

import Constants
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
object ApolloModule {

    @Provides
    @Singleton
    fun provideApolloClient(httpClient : OkHttpClient): ApolloClient {
        val apolloClient =
            ApolloClient
                .Builder()
                .okHttpClient(httpClient)
                .serverUrl(Constants.BASE_URL)
                .build()
        return apolloClient
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).build()
    }
}
