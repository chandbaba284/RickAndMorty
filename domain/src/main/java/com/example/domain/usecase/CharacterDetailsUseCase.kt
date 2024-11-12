package com.example.domain.usecase

import com.example.domain.mapper.CharacterDetailsMapper
import com.example.domain.repository.CharacterDetailsRepository
import javax.inject.Inject


class CharacterDetailsUseCase
@Inject
constructor(
    private val characterDetailsRepository : CharacterDetailsRepository,
){
    suspend fun invoke(id : String): Result<CharacterDetailsMapper> {
        return characterDetailsRepository.getCharacterDetailsById(id)
    }
}