package com.example.rickandmorty.data.di.module

import com.apollographql.apollo.ApolloClient
import com.example.rickandmorty.data.repository.CharactersRepositoryImpl
import com.example.rickandmorty.domain.repository.CharactersRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CharactersProvider {
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        val apolloClient =
            ApolloClient
                .Builder()
                .serverUrl("https://rickandmortyapi.com/graphql")
                .build()
        return apolloClient
    }

    @Provides
    @Singleton
    fun provideCharacters(apolloClient: ApolloClient): CharactersRepository = CharactersRepositoryImpl(apolloClient)
}
