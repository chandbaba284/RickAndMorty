package com.example.data.repository

import com.apollographql.apollo.ApolloClient
import com.example.common.module.ErrorMessages
import com.example.domain.mapper.CharacterDetails
import com.example.domain.mapper.toCharacterDetailsMapper
import com.example.domain.repository.CharacterDetailsRepository
import com.exmple.rickandmorty.GetCharacterDetailsByIdQuery

class CharacterDetailsRepositoryImpl(private val apolloClient: ApolloClient) :
    CharacterDetailsRepository {
    override suspend fun getCharacterDetailsById(id: String): Result<CharacterDetails> {
        return try {
            val response = apolloClient.query(GetCharacterDetailsByIdQuery(id)).execute()
            if (response.hasErrors()) {
                Result.failure(Exception(response.errors?.joinToString { it.message }))
            } else {
                response.data?.character?.let {
                    Result.success(
                        it.toCharacterDetailsMapper()
                    )
                } ?: Result.failure(Exception(ErrorMessages.NULL_RESPONSE_DATA))
            }
        } catch (e: Exception) {
            Result.failure(Exception("${ErrorMessages.FETCH_FAILED}: ${e.message}", e))

        }
    }
}