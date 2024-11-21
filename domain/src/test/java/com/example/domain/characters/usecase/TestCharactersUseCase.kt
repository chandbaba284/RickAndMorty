package com.example.domain.characters.usecase

import androidx.paging.PagingData
import com.example.domain.usecases.CharacterUseCase
import com.exmple.rickandmorty.GetCharactersQuery
import com.exmple.rickandmorty.fragment.Character
import com.exmple.rickandmorty.fragment.Location
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import repository.CharactersRepository
import usecases.CharacterUseCase

@ExperimentalCoroutinesApi
class TestCharactersUseCase {
    private lateinit var useCase: CharacterUseCase
    private var repository: CharactersRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        useCase = CharacterUseCase(repository)
    }

    @Test
    fun givenValidCharacters_whenGetCharactersCalled_thenReturnsMatchingCharacterList() =
        runTest(testDispatcher) {
            //Given
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
                            Character.Origin("", ""),
                            episode = listOf(Character.Episode("", "", "", ""))
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
                            Character.Origin("", ""),
                            episode = listOf(Character.Episode("", "", "", ""))
                        ),

                        ),
                )

            //When
            coEvery { repository.getCharacters() } returns flowOf(PagingData.from(items))
            //Then
            val expectedCharacters = repository.getCharacters().toList()
            val result = useCase.invoke().toList()
            Truth.assertThat(expectedCharacters).isEqualTo(result)
        }

}
