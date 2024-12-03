package com.example.domain.usecase
import com.example.common.module.DataState
import com.example.domain.mapper.CharacterDetails
import com.example.domain.repository.CharacterDetailsRepository
import javax.inject.Inject

class CharacterDetailsUseCase
@Inject
constructor(
    private val characterDetailsRepository: CharacterDetailsRepository,
) {
    suspend fun invoke(id: String): DataState<CharacterDetails> {
        return characterDetailsRepository.getCharacterDetailsById(id)
    }
}
