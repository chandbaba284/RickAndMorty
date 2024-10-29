package com.example.rickandmorty.domain.usecase

import com.example.rickandmorty.domain.repository.CharactersTestRepository
import com.example.rickandmorty.domain.repository.usecase.CharacterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersUseCaseTest {
    private lateinit var useCase: CharacterUseCase
    private lateinit var repository: CharactersTestRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = CharactersTestRepository()
        useCase = CharacterUseCase(repository)
    }

    @Test
    fun testInvokeCharactersWithRepository() =
        runTest(testDispatcher) {
            val expectedCharacters = repository.getCharacters().toList()
            val result = useCase.invokeCharacters().toList()
            assertEquals(expectedCharacters, result)
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset after tests
    }
}
