package com.example.data.repository

import com.apollographql.apollo.ApolloClient
import com.example.domain.mapper.EpisodeDetailsMapper
import com.example.domain.mapper.toEpisodeDetailsMapper
import com.exmple.rickandmorty.GetEpisodeDetailsByIdQuery

class EpisodeDetailsRepositoryImpl(private val apolloClient: ApolloClient) {
    suspend fun getEpisodeDetails(episodeId: String): Result<EpisodeDetailsMapper> {
        return try {
            val response = apolloClient.query(GetEpisodeDetailsByIdQuery(episodeId)).execute()
            if (response.hasErrors()) {
                Result.failure(Exception(response.errors?.joinToString { it.message }))
            } else {
                response.data?.let {
                    Result.success(it.toEpisodeDetailsMapper())

                }?:Result.failure(Exception())
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}