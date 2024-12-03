package com.example.domain.repository

import com.example.domain.mapper.EpisodeDetails

interface EpisodeDetailsRepository {
    suspend fun getEpisodeDetailsById(id: String): Result<EpisodeDetails>
}
