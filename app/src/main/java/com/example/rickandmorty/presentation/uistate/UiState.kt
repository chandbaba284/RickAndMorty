package com.example.rickandmorty.presentation.uistate

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

sealed class UiState<T> {
    class Empty<T : Any> : UiState<T>()

    class Loading<T : Any> : UiState<T>()

    data class Success<T : Any>(
        val data: Flow<PagingData<T>>,
    ) : UiState<T>()

    data class Error<T : Any>(
        val exception: Exception,
    ) : UiState<T>()
}
