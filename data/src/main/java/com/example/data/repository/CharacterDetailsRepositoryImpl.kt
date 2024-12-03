package com.example.data.repository

import com.apollographql.apollo.ApolloClient
import com.example.common.R
import com.example.common.module.DataState
import com.example.domain.mapper.CharacterDetails
import com.example.domain.mapper.toCharacterDetailsMapper
import com.example.domain.repository.CharacterDetailsRepository
import com.exmple.rickandmorty.GetCharacterDetailsByIdQuery

class CharacterDetailsRepositoryImpl(private val apolloClient: ApolloClient) :
    CharacterDetailsRepository {
    override suspend fun getCharacterDetailsById(id: String): DataState<CharacterDetails> {
        return try {
            val response = apolloClient.query(GetCharacterDetailsByIdQuery(id)).execute()
            if (response.hasErrors()) {
                DataState.Error(
                    IllegalStateException(response.errors?.joinToString { it.message }),
                    R.string.null_response_data
                )
            } else {
                response.data?.character?.let {
                    DataState.Success(
                        data =
                        it.toCharacterDetailsMapper()
                    )
                } ?: DataState.Error(IllegalStateException(), R.string.null_response_data)
            }
        } catch (e: Exception) {
            DataState.Error(IllegalStateException(), R.string.fetch_failed)
        }
    }
}
