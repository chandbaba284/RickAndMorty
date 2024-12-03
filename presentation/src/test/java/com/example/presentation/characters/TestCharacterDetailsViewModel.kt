package com.example.presentation.characters

import app.cash.turbine.test
import com.example.common.module.DataState
import com.example.domain.mapper.CharacterDetails
import com.example.domain.mapper.Episode
import com.example.domain.usecase.CharacterDetailsUseCase
import com.example.presentation.viewmodel.CharacterDetailsViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TestCharacterDetailsViewModel {
    private var characterDetailsUseCase: CharacterDetailsUseCase = mockk(relaxed = true)
    private lateinit var characterDetailsViewModel: CharacterDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        characterDetailsViewModel =
            CharacterDetailsViewModel(characterDetailsUseCase, testDispatcher)
    }

    @Test
    fun givenCharacterDetails_whenInvokeDetails_shouldEmitSuccess() {
        runTest(testDispatcher) {
            // Given
            val characterId = "1"
            coEvery { characterDetailsUseCase.invoke(characterId) } returns DataState.Success(
                CharacterDetails(
                    id = "1",
                    name = "Rick",
                    image = "",
                    status = "Alive",
                    species = "",
                    gender = "",
                    originName = "",
                    originDimension = "",
                    locationName = "",
                    locationDimension = "", episodes = listOf(Episode("", "", ""))
                )
            )
            // When
            characterDetailsViewModel.getCharacterDetails(characterId)

            // Then
            characterDetailsViewModel.characterDetails.test {
                assertThat(awaitItem()).isEqualTo(DataState.Loading)
                val successState = awaitItem()
                assertThat(successState).isInstanceOf(DataState.Success::class.java)
                val actualResponse = (successState as DataState.Success)
                val expectedCharacters = characterDetailsUseCase.invoke(characterId)
                assertThat(actualResponse).isEqualTo(expectedCharacters)
            }
        }
    }

    @Test
    fun givenWrongCharacterId_whenInvokeIsCalledInUseCase_shouldEmitError() {
        runTest(testDispatcher) {
            // Given
            val characterId = "-1"
            coEvery { characterDetailsUseCase.invoke(characterId) } returns DataState.Error(
                Exception(
                    "Character Details are Empty"
                )
            )
            // When
            characterDetailsViewModel.getCharacterDetails(characterId)
            // Then
            characterDetailsViewModel.characterDetails.test {
                assertThat(awaitItem()).isEqualTo(DataState.Loading)
                val errorState = awaitItem()
                val actualOutput = (errorState as DataState.Error).exception.message
                val result = characterDetailsUseCase.invoke(characterId)
                val expectedCharacters = result as DataState.Error
                assertThat(actualOutput).isEqualTo(expectedCharacters.exception.message)
            }
        }
    }
}
