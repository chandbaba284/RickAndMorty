package com.example.domain.usecase

import com.example.domain.mapper.CharacterDetails
import com.example.domain.repository.CharacterDetailsRepository
import javax.inject.Inject


class CharacterDetailsUseCase
@Inject
constructor(
    private val characterDetailsRepository : CharacterDetailsRepository,
){
    suspend fun invokeCharacterDetails(id : String): CharacterDetails? {
        return characterDetailsRepository.getCharacterDetailsById(id)
    }
}