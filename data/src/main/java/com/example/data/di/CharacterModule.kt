package com.example.data.di

import com.apollographql.apollo.ApolloClient
import com.example.data.repository.CharactersRepositoryImpl
import dagger.Module
import dagger.Provides
import repository.CharactersRepository
import javax.inject.Singleton

@Module
object CharacterModule {
    @Provides
    @Singleton
    fun provideCharacterModule(apolloClient: ApolloClient): CharactersRepository {
        return CharactersRepositoryImpl(apolloClient)
    }
}