package com.example.rickandmorty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.domain.repository.usecase.CharacterUseCase
import com.example.rickandmorty.presentation.uistate.UiState
import com.example.rickandmorty.utills.NetWorkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(private val charactersUseCase: CharacterUseCase) :
    ViewModel() {
    val _charactersstate = MutableStateFlow(UiState.CharactersScreenState())
    val charactersState: StateFlow<UiState.CharactersScreenState> get() = _charactersstate.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _charactersstate.emit(UiState.CharactersScreenState(isLoading = true))
            charactersUseCase.invokeFunction().collect { netWorkResult ->
                when (netWorkResult) {
                    is NetWorkResult.Loading -> {
                        _charactersstate.update { UiState.CharactersScreenState() }
                    }

                    is NetWorkResult.Success -> {
                        netWorkResult.data?.flow?.collect {
                            _charactersstate.update {
                                UiState.CharactersScreenState(
                                    data = netWorkResult.data
                                )
                            }

                        }
                    }
                    is NetWorkResult.Error -> {
                        _charactersstate.update {
                            UiState.CharactersScreenState(
                                error = netWorkResult.message.toString()
                            )

                        }

                    }
                }
            }
        }

    }

}

