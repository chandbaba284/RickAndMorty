package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.uistate.UiState
import com.exmple.rickandmorty.GetCharactersQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import usecases.CharacterUseCase
import javax.inject.Inject

class CharactersViewModel
@Inject
constructor(
    private val charactersUseCase: CharacterUseCase,
) : ViewModel() {
    private val _charactersState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val charactersState: StateFlow<UiState> get() = _charactersState

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _charactersState.emit(UiState.Loading)
            charactersUseCase
                .invokeCharacters().collect {
                    _charactersState.emit(UiState.Success(it))
                }

        }
    }
}
