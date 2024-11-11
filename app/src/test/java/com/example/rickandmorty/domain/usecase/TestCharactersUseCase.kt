package com.example.rickandmorty.domain.usecase

import com.example.rickandmorty.domain.repository.TestCharactersRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import repository.CharactersRepository
import usecases.CharacterUseCase

@ExperimentalCoroutinesApi
class TestCharactersUseCase {
    private lateinit var useCase: CharacterUseCase
    private var repository: CharactersRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var testCharactersRepository: TestCharactersRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        testCharactersRepository = TestCharactersRepository()
        useCase = CharacterUseCase(repository)
    }

    @Test
    fun testInvokeCharactersWithRepository() =
        runTest(testDispatcher) {
            coEvery { repository.getCharacters() } returns flowOf(testCharactersRepository.getCharacters())
            val expectedCharacters = repository.getCharacters().toList()
            val result = useCase.invokeCharacters().toList()
            println(expectedCharacters)
            println(result)
            assertEquals(expectedCharacters, result)
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset after tests
    }
}
