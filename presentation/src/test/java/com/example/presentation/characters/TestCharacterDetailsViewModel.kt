package com.example.presentation.characters

import app.cash.turbine.test
import com.example.domain.mapper.CharacterDetailsMapper
import com.example.domain.usecase.CharacterDetailsUseCase
import com.example.presentation.uistate.UiState
import com.example.presentation.viewmodel.CharacterDetailsViewModel
import com.exmple.rickandmorty.fragment.Character
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class TestCharacterDetailsViewModel {
    private var characterDetailsUseCase: CharacterDetailsUseCase = mockk(relaxed = true)
    private lateinit var characterDetailsViewModel: CharacterDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        characterDetailsViewModel = CharacterDetailsViewModel(characterDetailsUseCase)

    }

    @Test
    fun givenCharacterDetails_whenInvokeDetails_shouldEmitSuccess() {
        runTest(testDispatcher) {
            coEvery { characterDetailsUseCase.invoke("1") } returns getCharacterDetails("1")
            characterDetailsViewModel.characterDetails.test {
                characterDetailsViewModel.getCharacterDetails("1")
                assertThat(awaitItem()).isEqualTo(UiState.Loading)
                val successState = awaitItem()
                assertThat(successState).isInstanceOf(UiState.Success::class.java)
                val actualResponse = (successState as UiState.Success).data
                characterDetailsUseCase.invoke("1").onSuccess {
                    assertThat(actualResponse).isEqualTo(it)
                }.onFailure {
                    val errorState = awaitItem()
                    val actualOutput = (errorState as UiState.Error)
                    val expectedOutput = "Character Details are Empty"
                    println(actualOutput)
                    println(expectedOutput)
                    assertThat(actualOutput).isEqualTo(expectedOutput)
                }
            }
        }
    }

}

fun getCharacterDetails(characterId: String): Result<CharacterDetailsMapper> {
    return if (characterId.isNotEmpty()) {
        Result.success(
            CharacterDetailsMapper(
                id = "1",
                name = "Rick",
                image = "",
                status = "Alive",
                species = "",
                gender = "",
                originName = "",
                originDimension = "",
                locationName = "",
                locationDimension = "", episodes = listOf(Character.Episode("", "", ""))
            )
        )
    } else {
        Result.failure(Exception("Character Details are Empty"))
    }

}