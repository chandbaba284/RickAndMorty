package com.example.rickandmorty.domain.repository.usecase

import androidx.paging.Pager
import com.example.rickandmorty.domain.repository.CharactersRepository
import com.example.rickandmorty.utills.NetWorkResult
import com.exmple.rickandmorty.GetCharactersQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterUseCase
    @Inject
    constructor(
        private val charactersRepository: CharactersRepository,
    ) {
        fun invokeFunction(): Flow<NetWorkResult<Pager<Int, GetCharactersQuery.Result>>> =
            flow {
                emit(NetWorkResult.Loading())
                val response = charactersRepository.getCharacters()
                emit(NetWorkResult.Success(response))
            }
    }
