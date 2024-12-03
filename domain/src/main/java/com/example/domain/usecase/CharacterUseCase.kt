package com.example.domain.usecase

import androidx.paging.PagingData
import com.exmple.rickandmorty.GetCharactersQuery
import kotlinx.coroutines.flow.Flow
import repository.CharactersRepository
import javax.inject.Inject

class CharacterUseCase
@Inject
constructor(
    private val charactersRepository:
    CharactersRepository,
) {
    suspend fun invoke(): Flow<PagingData<GetCharactersQuery.Result>> {
        return charactersRepository.getCharacters()
    }
}
