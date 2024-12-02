package com.example.data.repository

import com.apollographql.apollo.ApolloClient
import com.example.common.module.DataState
import com.example.common.R
import com.example.domain.mapper.EpisodeDetails
import com.example.domain.mapper.toEpisodeDetailsMapper
import com.exmple.rickandmorty.GetEpisodeDetailsByIdQuery

class EpisodeDetailsRepositoryImpl(private val apolloClient: ApolloClient) {
    suspend fun getEpisodeDetails(episodeId: String): DataState<EpisodeDetails> {
        return try {
            val response = apolloClient.query(GetEpisodeDetailsByIdQuery(episodeId)).execute()
            if (response.hasErrors()) {
                DataState.Error(IllegalStateException(response.errors?.joinToString { it.message }),
                    R.string.null_response_data)
            } else {
                response.data?.let {
                    DataState.Success(it.toEpisodeDetailsMapper())

                }?:DataState.Error(IllegalStateException(),R.string.null_response_data)
            }

        } catch (e: Exception) {
            DataState.Error(IllegalStateException(),R.string.fetch_failed)
        }
    }
}