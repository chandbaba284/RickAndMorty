package com.example.rickandmorty.presentation.viewmodel

import com.example.presentation.uistate.UiState
import com.example.presentation.viewmodel.CharactersViewModel
import com.example.rickandmorty.domain.repository.TestCharactersRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
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

    @Test
    fun testFetchCharacterUpdatesToUi() =
        runTest(testDispatcher) {
            val mockPagingData = testCharactersRepository.getCharacters()
            coEvery { useCase.invokeCharacters() } returns mockPagingData
            charactersViewModel.fetchData()
            val job =
                launch {
                    charactersViewModel.charactersState.collect { state ->
                        println(state)
                        when (state) {
                            is UiState.Success -> {
                                println(mockPagingData)
                                println(state.data)
                                assertEquals(mockPagingData, state.data)
                            }
                            UiState.Empty -> {}
                            is UiState.Error -> {}
                            UiState.Loading -> {}
                        }
                    }
                }
            testDispatcher.scheduler.advanceUntilIdle()
            job.cancel()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
