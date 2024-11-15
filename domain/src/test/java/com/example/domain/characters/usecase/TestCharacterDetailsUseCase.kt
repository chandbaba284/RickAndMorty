package com.example.domain.characters.usecase

import com.example.domain.mapper.CharacterDetailsMapper
import com.example.domain.repository.CharacterDetailsRepository
import com.example.domain.usecase.CharacterDetailsUseCase
import com.exmple.rickandmorty.fragment.Character
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TestCharacterDetailsUseCase {
    private lateinit var characterDetailsUseCase: CharacterDetailsUseCase
    private  var characterDetailsRepository : CharacterDetailsRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp(){
        characterDetailsUseCase = CharacterDetailsUseCase(characterDetailsRepository)
    }

    @Test
    fun givenCharacterDetails_whenGetCharacterDetailsCalled_thenMatchResultOfUseCase(){
        runTest(testDispatcher) {
            //Given
            val characterId = "1"
            val characterDetails = Result.success(
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
            coEvery { characterDetailsRepository.getCharacterDetailsById(characterId)} returns characterDetails
            //When
            val expectedOutPut = characterDetailsRepository.getCharacterDetailsById(characterId)
            val actualCharacters = characterDetailsUseCase.invoke(characterId)

            //Then
            Truth.assertThat(actualCharacters).isEqualTo(expectedOutPut)

        }
    }

    @Test
    fun givenCharacterDetails_whenGetCharacterIdIsWrongShouldReturnException(){
        runTest(testDispatcher) {
            //Given
            val characterId = "1"
            coEvery { characterDetailsRepository.getCharacterDetailsById(characterId)} returns Result.failure(Exception("No Data Found"))
            //When
            val expectedCharacters = characterDetailsRepository.getCharacterDetailsById(characterId)
            val actualCharacters = characterDetailsUseCase.invoke(characterId)
            //Then
            Truth.assertThat(actualCharacters).isEqualTo(expectedCharacters)

        }
    }


}
