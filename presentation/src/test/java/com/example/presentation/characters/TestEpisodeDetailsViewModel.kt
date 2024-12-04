package com.example.presentation.characters

import app.cash.turbine.test
import com.example.common.R
import com.example.common.module.DataState
import com.example.domain.mapper.Character
import com.example.domain.mapper.EpisodeDetails
import com.example.domain.usecase.EpisodeDetailsUseCase
import com.example.presentation.viewmodel.EpisodeDetailsViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TestEpisodeDetailsViewModel {
    private var episodeDetailsUseCase: EpisodeDetailsUseCase = mockk()
    private lateinit var episodeDetailsViewModel: EpisodeDetailsViewModel
    val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        episodeDetailsViewModel = EpisodeDetailsViewModel(episodeDetailsUseCase, testDispatcher)
    }

    @Test
    fun givenEpisodeDetails_WhenInvokeIsCalled_ShouldEmitEpisodeDetails() {
        runTest(testDispatcher) {
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
            coEvery { episodeDetailsUseCase.invoke(episodeId) } returns episodeDetails

            // When
            episodeDetailsViewModel.getEpisodeDetailsByUsingEpisodeId(episodeId)

            // Then
            episodeDetailsViewModel.episodeDetails.test {
                assertThat(awaitItem()).isEqualTo(DataState.Loading)
                val successState = awaitItem()
                assertThat(successState).isInstanceOf(DataState.Success::class.java)
                val actualResponse = (successState as DataState.Success)
                val expectedResponse = episodeDetailsUseCase.invoke(episodeId)
                assertThat(actualResponse).isEqualTo(expectedResponse)
            }
        }
    }

    @Test
    fun givenWrongEpisodeId_whenInvokeIsCalledInUseCase_shouldEmitError() {
        runTest(testDispatcher) {
            // Given
            val episodeId = "-1"
            coEvery { episodeDetailsUseCase.invoke(episodeId) } returns DataState.Error(
                Exception(),
                R.string.fetch_failed
            )
            // When
            episodeDetailsViewModel.getEpisodeDetailsByUsingEpisodeId(episodeId)
            // Then
            episodeDetailsViewModel.episodeDetails.test {
                assertThat(awaitItem()).isEqualTo(DataState.Loading)
                val errorState = awaitItem()
                val actualOutput = (errorState as DataState.Error).errorMessage
                val result = episodeDetailsUseCase.invoke(episodeId)
                val expectedCharacters = result as DataState.Error
                assertThat(actualOutput).isEqualTo(expectedCharacters.errorMessage)
            }
        }
    }
}
