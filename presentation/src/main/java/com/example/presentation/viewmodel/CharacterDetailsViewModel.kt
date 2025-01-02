package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.module.DataState
import com.example.data.di.IoDispatcher
import com.example.domain.mapper.CharacterDetails
import com.example.domain.usecase.CharacterDetailsUseCase
import dagger.assisted.Assisted
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val characterDetailsUseCase: CharacterDetailsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @Assisted private var characterId: String
) : ViewModel() {

    private val _characterDetails: MutableStateFlow<DataState<CharacterDetails>> = MutableStateFlow(
        DataState.Loading
    )
    val characterDetails: StateFlow<DataState<CharacterDetails>> = _characterDetails
    var characterNameForTopBar = ""
        private set


    fun getCharacterDetails(character: String) {
        viewModelScope.launch(ioDispatcher) {
            if (characterId.isEmpty()) {
                val result = characterDetailsUseCase.invoke(characterId)
                when (result) {
                    is DataState.Success -> {
                        characterNameForTopBar = result.data.name
                        _characterDetails.emit(DataState.Success(result.data))
                    }
                    is DataState.Error -> {
                        _characterDetails.emit(result)
                    }
                    DataState.Loading -> {
                        _characterDetails.emit(DataState.Loading)
                    }
                }
                characterId = character
            }
        }
    }

    fun getCharacterName(): String {
        return characterNameForTopBar
    }
}
