package com.example.data.repository

import com.apollographql.apollo.ApolloClient
import com.example.domain.mapper.CharacterDetailsMapper
import com.example.domain.mapper.toCharacterDetailsMapper
import com.example.domain.repository.CharacterDetailsRepository
import com.exmple.rickandmorty.GetCharacterDetailsByIdQuery

class CharacterDetailsRepositoryImpl(private val apolloClient: ApolloClient) :
    CharacterDetailsRepository {
    override suspend fun getCharacterDetailsById(id: String): Result<CharacterDetailsMapper> {
        return try {
            val response = apolloClient.query(GetCharacterDetailsByIdQuery(id)).execute()
            if (response.hasErrors()) {
                Result.failure(Exception(response.errors?.joinToString { it.message }))
            } else {
                response.data?.character?.let {
                    Result.success(
                        it.toCharacterDetailsMapper()
                    )
                } ?: Result.failure(Exception())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}