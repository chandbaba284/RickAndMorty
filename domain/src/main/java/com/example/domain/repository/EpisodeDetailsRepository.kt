package com.example.domain.repository

import com.example.domain.mapper.EpisodeDetailsMapper

interface EpisodeDetailsRepository {
    suspend fun getEpisodeDetailsById(id : String) : Result<EpisodeDetailsMapper>
}