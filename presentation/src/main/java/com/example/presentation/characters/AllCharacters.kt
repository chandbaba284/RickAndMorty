
package com.example.presentation.characters

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.presentation.viewmodel.CharactersViewModel
import com.example.presentation.uistate.UiState

@Composable
fun AllCharacters(viewModel: CharactersViewModel) {
    val uistate = viewModel.charactersState.collectAsState().value
    when (uistate) {
        is UiState.Empty -> {}
        is UiState.Error -> {}
        is UiState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.size(100.dp))
        }

        is UiState.Success -> {
            val charactersList = uistate.data.collectAsLazyPagingItems()
            CharactersList(charactersList)

        }
   }
}
