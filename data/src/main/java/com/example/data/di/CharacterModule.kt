package com.example.data.di

import com.apollographql.apollo.ApolloClient
import com.example.data.repository.CharacterDetailsRepositoryImpl
import com.example.data.repository.CharactersRepositoryImpl
import com.example.domain.repository.CharacterDetailsRepository
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

    @Provides
    @Singleton
    fun provideCharacterDetails(apolloClient: ApolloClient) : CharacterDetailsRepository{
        return CharacterDetailsRepositoryImpl(apolloClient)
    }
}

