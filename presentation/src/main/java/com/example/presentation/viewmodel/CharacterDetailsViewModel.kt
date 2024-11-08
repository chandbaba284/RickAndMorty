package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.mapper.CharacterDetailsMapper
import com.example.domain.usecase.CharacterDetailsUseCase
import com.example.presentation.uistate.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(val characterDetailsUseCase : CharacterDetailsUseCase) : ViewModel() {

    val _characterDetails : MutableStateFlow<UiState<CharacterDetailsMapper>> = MutableStateFlow(UiState.Loading)
    val characterDetails : StateFlow<UiState<CharacterDetailsMapper>> = _characterDetails
    private var characterName = ""

    fun getCharacterDetails(characterId : String){
        viewModelScope.launch {
          val result = characterDetailsUseCase.invokeCharacterDetails(characterId)
            result.onSuccess {characterDetails->
                characterName = characterDetails.name?:""
                _characterDetails.emit(UiState.Success(characterDetails))
            }.onFailure {
                _characterDetails.emit(UiState.Error(Exception("Character Details are Empty")))
            }

        }
    }

    fun getCharacterName(): String {
        return characterName
    }
}