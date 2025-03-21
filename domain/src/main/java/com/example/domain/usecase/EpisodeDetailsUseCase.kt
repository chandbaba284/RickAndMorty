package com.example.domain.usecase

import com.example.common.module.DataState
import com.example.domain.mapper.EpisodeDetails
import com.example.domain.repository.EpisodeDetailsRepository
import javax.inject.Inject

class EpisodeDetailsUseCase @Inject constructor(private val episodeDetailsRepository: EpisodeDetailsRepository) {
    suspend fun invoke(episodeId: String): DataState<EpisodeDetails> {
        return episodeDetailsRepository.getEpisodeDetailsById(episodeId)
    }
}
