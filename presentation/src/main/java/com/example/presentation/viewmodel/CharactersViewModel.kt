package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
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
    private val _charactersState: MutableStateFlow<UiState<PagingData<GetCharactersQuery.Result>>> = MutableStateFlow(UiState.Loading)
    val charactersState: StateFlow<UiState<PagingData<GetCharactersQuery.Result>>> get() = _charactersState

    init {
        fetchData()
    }
    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            charactersUseCase
                .invoke()
                .cachedIn(viewModelScope)
                .onStart {
                    _charactersState.emit(UiState.Loading)
                }
                .catch {
                    _charactersState.emit(UiState.Error(Exception(it.message)))
                }
                .collect {
                    _charactersState.emit(UiState.Success(it))
                }
        }
    }
}
