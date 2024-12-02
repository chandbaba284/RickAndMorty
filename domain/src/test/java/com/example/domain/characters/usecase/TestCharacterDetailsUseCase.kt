package com.example.domain.characters.usecase

import com.example.common.module.DataState
import com.example.domain.mapper.CharacterDetails
import com.example.domain.mapper.Episode
import com.example.domain.repository.CharacterDetailsRepository
import com.example.domain.usecase.CharacterDetailsUseCase
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TestCharacterDetailsUseCase {
    private lateinit var characterDetailsUseCase: CharacterDetailsUseCase
    private  var characterDetailsRepository : CharacterDetailsRepository = mockk()


    @Before
    fun setUp(){
        characterDetailsUseCase = CharacterDetailsUseCase(characterDetailsRepository)
    }

    @Test
    fun givenCharacterDetails_whenGetCharacterDetailsCalled_thenMatchResultOfUseCase(){
        runTest {
            //Given
            val characterId = "1"
            val characterDetails = DataState.Success(
                CharacterDetails(
                    id = "1",
                    name = "Rick",
                    image = "",
                    status = "Alive",
                    species = "",
                    gender = "",
                    originName = "",
                    originDimension = "",
                    locationName = "",
                    locationDimension = "", episodes = listOf(Episode("","",""))
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
        runTest {
            //Given
            val characterId = "1"
            coEvery { characterDetailsRepository.getCharacterDetailsById(characterId)} returns DataState.Error(Exception("No Data Found"))
            //When
            val expectedCharacters = characterDetailsRepository.getCharacterDetailsById(characterId)
            val actualCharacters = characterDetailsUseCase.invoke(characterId)
            //Then
            Truth.assertThat(actualCharacters).isEqualTo(expectedCharacters)

        }
    }


}
