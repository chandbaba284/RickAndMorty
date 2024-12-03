package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.module.DataState
import com.example.data.di.IoDispatcher
import com.example.domain.mapper.CharacterDetails
import com.example.domain.usecase.CharacterDetailsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val characterDetailsUseCase: CharacterDetailsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _characterDetails: MutableStateFlow<DataState<CharacterDetails>> = MutableStateFlow(
        DataState.Loading
    )
    val characterDetails: StateFlow<DataState<CharacterDetails>> = _characterDetails
    var characterNameForTopBar = ""
        private set
    var characterId = ""
        private set

    fun getCharacterDetails(characterId: String) {
        viewModelScope.launch(ioDispatcher) {
            if (this@CharacterDetailsViewModel.characterId.isEmpty()) {
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
                this@CharacterDetailsViewModel.characterId = characterId
            }
        }
    }

    fun getCharacterName(): String {
        return characterNameForTopBar
    }
}
