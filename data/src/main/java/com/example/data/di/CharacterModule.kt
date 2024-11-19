package com.example.data.di

import com.apollographql.apollo.ApolloClient
import com.example.data.repository.CharacterDetailsRepositoryImpl
import com.example.data.repository.CharactersRepositoryImpl
import com.example.data.repository.EpisodeDetailsRepositoryImpl
import com.example.domain.repository.CharacterDetailsRepository
import com.example.domain.repository.EpisodeDetailsRepository
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
    fun provideCharacterDetails(apolloClient: ApolloClient): CharacterDetailsRepository {
        return CharacterDetailsRepositoryImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideEpisodeDetails(apolloClient: ApolloClient) : EpisodeDetailsRepository{
        return EpisodeDetailsRepositoryImpl(apolloClient)
    }
}
