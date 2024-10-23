package com.example.rickandmorty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.domain.repository.usecase.CharacterUseCase
import com.example.rickandmorty.presentation.uistate.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel
    @Inject
    constructor(
        private val charactersUseCase: CharacterUseCase,
    ) : ViewModel() {
        val _charactersstate: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
        val charactersState: StateFlow<UiState> get() = _charactersstate.asStateFlow()

        init {
            fetchData()
        }

        private fun fetchData() {
            viewModelScope.launch {
                charactersUseCase
                    .invokeCharacters()
                    .onStart {
                        _charactersstate.update { UiState.Loading }
                    }.catch { UiState.Empty }
                    .collect {
                        _charactersstate.update { UiState.Success(charactersUseCase.invokeCharacters()) }
                    }
            }
        }
    }
