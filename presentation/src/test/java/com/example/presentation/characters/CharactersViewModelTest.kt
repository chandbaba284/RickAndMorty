package com.example.presentation.characters

import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.common.module.DataState
import com.example.domain.usecase.CharacterUseCase
import com.example.presentation.viewmodel.CharactersViewModel
import com.exmple.rickandmorty.GetCharactersQuery
import com.exmple.rickandmorty.fragment.Character
import com.exmple.rickandmorty.fragment.Location
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class CharactersViewModelTest {
    private var useCase: CharacterUseCase = mockk(relaxed = true)
    private lateinit var charactersViewModel: CharactersViewModel
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        charactersViewModel = CharactersViewModel(useCase, testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun givenValidCharacters_whenInvokeIsCalledInUseCase_thenListSizeShouldMatchWithStateFlowItemsSize() =
        runTest(testDispatcher) {
            // Given
            val items =
                listOf(
                    GetCharactersQuery.Result(
                        "",
                        Character(
                            name = "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            location = Character.Location("", Location("", "", "")),
                            Character.Origin("", ""), episode = listOf(Character.Episode("", "", "", ""))
                        ),
                    ),
                    GetCharactersQuery.Result(
                        "",
                        Character(
                            name = "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            Character.Location(
                                "",
                                Location("", "", ""),
                            ),
                            Character.Origin("", ""), episode = listOf(Character.Episode("", "", "", ""))
                        ),

                    ),
                )

            val pagedData = PagingData.from(items)
            // When
            coEvery { useCase.invoke() } returns flowOf(pagedData)
            // Then
            charactersViewModel.charactersState.test {
                charactersViewModel.fetchData()
                assertThat(awaitItem()).isEqualTo(DataState.Loading)
                advanceUntilIdle()
                val successState = awaitItem()
                assertThat(successState).isInstanceOf(DataState.Success::class.java)
                val expectedCharacters = useCase.invoke().toList()
                val actualCharacters = flowOf((successState as DataState.Success).data).toList()
                assertThat(actualCharacters.size).isEqualTo(expectedCharacters.size)
                cancelAndConsumeRemainingEvents()
            }
        }
}
