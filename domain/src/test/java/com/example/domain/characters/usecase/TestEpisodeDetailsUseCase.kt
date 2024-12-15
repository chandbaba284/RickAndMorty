package com.example.domain.characters.usecase

import com.example.common.module.DataState
import com.example.domain.mapper.Character
import com.example.domain.mapper.EpisodeDetails
import com.example.domain.repository.EpisodeDetailsRepository
import com.example.domain.usecase.EpisodeDetailsUseCase
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TestEpisodeDetailsUseCase {

    private lateinit var episodeDetailsUseCase: EpisodeDetailsUseCase
    private var episodeDetailsRepository: EpisodeDetailsRepository = mockk()

    @Before
    fun setup() {
        episodeDetailsUseCase = EpisodeDetailsUseCase(episodeDetailsRepository)
    }

    @Test
    fun givenEpisodeDetailsBasedOnID_WhenGetEpisodeDetailsRepositoryCalled_ShouldMatchUseCaseResult() {
        runTest {
            // Given
            val episodeId = "1"
            val episodeDetails = DataState.Success(
                EpisodeDetails(
                    id = "",
                    airDate = "",
                    title = "",
                    characters = listOf(
                        Character("", "", "")
                    )
                )
            )

            // When
            coEvery { episodeDetailsRepository.getEpisodeDetailsById(episodeId) } returns episodeDetails

            // Then
            val expectedOutPut = episodeDetailsRepository.getEpisodeDetailsById(episodeId)
            val actualOutPut = episodeDetailsUseCase.invoke(episodeId)
            Truth.assertThat(actualOutPut).isEqualTo(expectedOutPut)
        }
    }

    @Test
    fun givenWrongEpisodeId_WhenGetEpisodeDetailsRepositoryCalled_ShouldThrowException() {
        runTest {
            // Given
            val episodeId = "-1"
            // When
            coEvery { episodeDetailsRepository.getEpisodeDetailsById(episodeId) } returns DataState.Error(
                IllegalStateException(),
                com.example.common.R.string.fetch_failed
            )
            // Then
            val expectedOutPut = episodeDetailsRepository.getEpisodeDetailsById(episodeId)
            val actualOutput = episodeDetailsUseCase.invoke(episodeId)
            Truth.assertThat(actualOutput).isEqualTo(expectedOutPut)
        }
    }
}
