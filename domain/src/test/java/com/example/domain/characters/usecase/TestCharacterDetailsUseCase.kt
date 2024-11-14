package com.example.domain.characters.usecase

import com.example.domain.mapper.CharacterDetailsMapper
import com.example.domain.repository.CharacterDetailsRepository
import com.example.domain.usecase.CharacterDetailsUseCase
import com.exmple.rickandmorty.fragment.Character
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class TestCharacterDetailsUseCase {
    private lateinit var characterDetailsUseCase: CharacterDetailsUseCase
    private  var characterDetailsRepository : CharacterDetailsRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        Dispatchers.setMain(testDispatcher)
        characterDetailsUseCase = CharacterDetailsUseCase(characterDetailsRepository)
    }

    @Test
    fun givenCharacterDetails_whenGetCharacterDetailsCalled_thenMatchResultOfUseCase(){
        runTest(testDispatcher) {
            var characterDetails = getCharacterDetails()
            coEvery { characterDetailsRepository.getCharacterDetailsById("1")} returns characterDetails
            val expectedOutPut = characterDetailsRepository.getCharacterDetailsById("1")
            val actualCharacters = characterDetailsUseCase.invoke("1")
            Truth.assertThat(actualCharacters).isEqualTo(expectedOutPut)

        }

    }

    @Test
    fun givenCharacterDetails_whenGetCharacterIdIsWrongShouldReturnException(){
        runTest(testDispatcher) {
            coEvery { characterDetailsRepository.getCharacterDetailsById("-1")} returns Result.failure(Exception("No Data Found"))
            val expectedCharacters = characterDetailsRepository.getCharacterDetailsById("-1")
            val actualCharacters = characterDetailsUseCase.invoke("-1")
            Truth.assertThat(actualCharacters).isEqualTo(expectedCharacters)

        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset after tests
    }

}
fun getCharacterDetails(): Result<CharacterDetailsMapper> {
    return Result.success(
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
            locationDimension = "", episodes = listOf(Character.Episode("","",""))
        )
    )

}