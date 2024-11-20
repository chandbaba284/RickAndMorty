package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.di.IoDispatcher
import com.example.domain.mapper.CharacterDetailsMapper
import com.example.domain.usecase.CharacterDetailsUseCase
import com.example.presentation.uistate.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(private val characterDetailsUseCase : CharacterDetailsUseCase, @IoDispatcher private val ioDispatcher: CoroutineDispatcher,) : ViewModel() {

    private val _characterDetails : MutableStateFlow<UiState<CharacterDetailsMapper>> = MutableStateFlow(UiState.Loading)
    val characterDetails : StateFlow<UiState<CharacterDetailsMapper>> = _characterDetails
    var characterNameForTopBar= ""
        private set

    fun getCharacterDetails(characterId : String){
        viewModelScope.launch(ioDispatcher) {
          val result = characterDetailsUseCase.invoke(characterId)
            result.onSuccess {characterDetails->
                characterNameForTopBar = characterDetails.name?:""
                _characterDetails.emit(UiState.Success(characterDetails))
            }.onFailure {
                _characterDetails.emit(UiState.Error(Exception("Character Details are Empty")))
            }

        }
    }

    fun getCharacterName(): String {
        return characterNameForTopBar
    }
}