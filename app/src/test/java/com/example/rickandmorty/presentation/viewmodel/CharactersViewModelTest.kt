package com.example.rickandmorty.presentation.viewmodel

import app.cash.turbine.test
import com.example.presentation.uistate.UiState
import com.example.presentation.viewmodel.CharactersViewModel
import com.example.rickandmorty.domain.repository.TestCharactersRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import usecases.CharacterUseCase

class CharactersViewModelTest {
    private var useCase: CharacterUseCase = mockk(relaxed = true)
    private lateinit var charactersViewModel: CharactersViewModel
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var testCharactersRepository: TestCharactersRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        charactersViewModel = CharactersViewModel(useCase)
        testCharactersRepository = TestCharactersRepository()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFetchCharacterUpdatesToUi() =
        runTest(testDispatcher) {
            val mockPagingData = flowOf(testCharactersRepository.getCharacters())
            coEvery { useCase.invokeCharacters() } returns mockPagingData
            charactersViewModel.charactersState.test {
                charactersViewModel.fetchData()
                assertEquals(UiState.Loading, awaitItem())
                val successState = awaitItem()
                assert(successState is UiState.Success) { "Expected Success but was $successState" }
                val expectedCharacters = mockPagingData.toList()
                val actualCharacters = flowOf((successState as UiState.Success).data).toList()
                assertEquals(expectedCharacters, actualCharacters)
                cancelAndConsumeRemainingEvents()
            }
            advanceUntilIdle()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
