package com.example.rickandmorty.data.repository

import CharacterPagingSource
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.apollographql.apollo.ApolloClient
import com.example.rickandmorty.domain.repository.CharactersRepository
import com.exmple.rickandmorty.GetCharactersQuery
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(val apolloClient: ApolloClient) :
    CharactersRepository {
    override suspend fun getCharacters(): Pager<Int, GetCharactersQuery.Result> {
        return Pager(
            config = PagingConfig(pageSize = 20), // Set your desired page size
            pagingSourceFactory = { CharacterPagingSource(apolloClient) }
        )
    }
}