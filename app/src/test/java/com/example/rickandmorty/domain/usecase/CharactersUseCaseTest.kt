package com.example.rickandmorty.domain.usecase

import com.example.rickandmorty.domain.repository.CharactersTestRepository
import com.example.rickandmorty.domain.repository.usecase.CharacterUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersUseCaseTest {

    private lateinit var useCase: CharacterUseCase
    private lateinit var repository: CharactersTestRepository

    @Before
    fun setup() {
        repository = CharactersTestRepository()
        useCase = CharacterUseCase(repository)
    }

    @Test
    fun `test invoke returns dummy characters`() = runBlocking {
        val expectedCharacters = repository.getCharacters().toList()
        val result = useCase.invokeCharacters().toList()
        assertEquals(expectedCharacters, result)
    }
}