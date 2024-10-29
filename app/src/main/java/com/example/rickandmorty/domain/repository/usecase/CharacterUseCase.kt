package com.example.rickandmorty.domain.repository.usecase

import androidx.paging.PagingData
import com.example.rickandmorty.domain.repository.CharactersRepository
import com.exmple.rickandmorty.GetCharactersQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterUseCase
    @Inject
    constructor(
        private val charactersRepository: CharactersRepository,
    ) {
        suspend fun invokeCharacters(): Flow<PagingData<GetCharactersQuery.Result>> = charactersRepository.getCharacters()
    }
