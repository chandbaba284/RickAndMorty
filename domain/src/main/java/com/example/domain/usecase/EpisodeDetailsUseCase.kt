package com.example.domain.usecase

import com.example.domain.mapper.EpisodeDetailsMapper
import com.example.domain.repository.EpisodeDetailsRepository
import javax.inject.Inject

class EpisodeDetailsUseCase @Inject constructor(private val episodeDetailsRepository: EpisodeDetailsRepository) {
    suspend fun invoke(episodeId : String): Result<EpisodeDetailsMapper> {
        return episodeDetailsRepository.getEpisodeDetailsById(episodeId)
    }
}