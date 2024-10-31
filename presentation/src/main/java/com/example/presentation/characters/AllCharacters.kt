package com.example.presentation.characters

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.presentation.viewmodel.CharactersViewModel
import com.example.presentation.uistate.UiState.Empty
import com.example.presentation.uistate.UiState.Success
import com.example.presentation.uistate.UiState.Error
import com.example.presentation.uistate.UiState.Loading

@Composable
fun AllCharacters(viewModel: CharactersViewModel) {
    val uistate = viewModel.charactersState.collectAsState().value
    when (uistate) {
        is Empty -> {}
        is Error -> {}
        is Loading -> {
            CircularProgressIndicator(modifier = Modifier.size(100.dp))
        }
        is Success -> {
            val charactersList = uistate.data.collectAsLazyPagingItems()
            CharactersList(charactersList)
        }
        else -> Unit
    }
}
