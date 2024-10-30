package usecases

import androidx.paging.PagingData
import repository.CharactersRepository
import com.exmple.rickandmorty.GetCharactersQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterUseCase
@Inject
constructor(
    private val charactersRepository: CharactersRepository,
) {
    suspend fun invokeCharacters(): Flow<PagingData<GetCharactersQuery.Result>> =
        charactersRepository.getCharacters()
}
