package com.example.rickandmorty.presentation.viewmodel

import com.example.rickandmorty.domain.repository.CharactersTestRepository
import com.example.rickandmorty.domain.repository.usecase.CharacterUseCase
import com.example.rickandmorty.presentation.CharactersViewModel
import com.example.rickandmorty.presentation.uistate.UiState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class CharactersViewModelTest {
    private lateinit var useCase: CharacterUseCase
    private lateinit var charactersViewModel: CharactersViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val repository = CharactersTestRepository()
        useCase = CharacterUseCase(repository)
        charactersViewModel = CharactersViewModel(useCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test fetching characters updates UiState to Success`() =
        testScope.runTest {
            charactersViewModel.fetchData()

            useCase
                .invokeCharacters()
                .onStart {
                    assertEquals(UiState.Loading, charactersViewModel.charactersState.value)
                }.catch {
                    assertEquals(UiState.Empty, charactersViewModel.charactersState.value)
                }.collect { data ->
                    assertEquals(data, charactersViewModel.charactersState.value)
                }
        }
}
