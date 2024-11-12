package com.example.data.repository

import com.apollographql.apollo.ApolloClient
import com.example.domain.mapper.CharacterDetailsMapper
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
                val characterDetails = response.data?.character?.character
                var episodes = characterDetails?.episode
                characterDetails?.let {
                    Result.success(
                        CharacterDetailsMapper(
                            id = it.id,
                            name = it.name,
                            image = it.image,
                            status = it.status,
                            species = it.species,
                            gender = it.gender,
                            originName = it.origin?.name,
                            originDimension = it.origin?.dimension,
                            locationName = it.location?.location?.name,
                            locationDimension = it.location?.location?.dimension, episodes = episodes
                        )
                    )
                } ?: Result.failure(Exception())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}