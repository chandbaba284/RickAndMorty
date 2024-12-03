package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.common.module.DataState
import com.example.data.di.IoDispatcher
import com.example.domain.usecase.CharacterUseCase
import com.exmple.rickandmorty.GetCharactersQuery
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel
@Inject
constructor(
    private val charactersUseCase: CharacterUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _charactersState: MutableStateFlow<DataState<PagingData<GetCharactersQuery.Result>>> =
        MutableStateFlow(
            DataState.Loading
        )
    val charactersState: StateFlow<DataState<PagingData<GetCharactersQuery.Result>>> get() = _charactersState

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch(ioDispatcher) {
            charactersUseCase
                .invoke()
                .cachedIn(viewModelScope)
                .onStart {
                    _charactersState.emit(DataState.Loading)
                }
                .catch {
                    _charactersState.emit(DataState.Error(Exception(it.message)))
                }
                .collect {
                    _charactersState.emit(DataState.Success(it))
                }
        }
    }
}
