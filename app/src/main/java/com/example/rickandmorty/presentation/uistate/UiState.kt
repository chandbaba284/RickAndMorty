package com.example.rickandmorty.presentation.uistate

import androidx.paging.Pager
import com.exmple.rickandmorty.GetCharactersQuery

object UiState {
    data class CharactersScreenState(
        val isLoading: Boolean? = false,
        val error: String? = "",
        val data: Pager<Int, GetCharactersQuery.Result>? = null,
    )
}
