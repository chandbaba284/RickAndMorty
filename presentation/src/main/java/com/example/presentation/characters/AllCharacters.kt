package com.example.presentation.characters

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.presentation.R
import com.example.presentation.uistate.UiState
import com.example.presentation.uistate.UiState.Success
import com.example.presentation.uistate.UiState.Error
import com.example.presentation.uistate.UiState.Loading
import com.exmple.rickandmorty.GetCharactersQuery
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf

@Composable
fun AllCharacters(
    allCharacters: StateFlow<UiState<PagingData<GetCharactersQuery.Result>>>,
    innerPadding: PaddingValues,
    onNavigate: (String) -> Unit
    ) {
    val uistate = allCharacters.collectAsState().value
    when (uistate) {
        is Error -> {
            Text(text = uistate.exception.message.toString())
        }
        is Loading -> {
            CircularProgressIndicator(modifier = Modifier.size(dimensionResource(R.dimen.progress_bar_size)))
        }
        is Success<PagingData<GetCharactersQuery.Result>> -> {
            val charactersList = remember { flowOf(uistate.data) }.collectAsLazyPagingItems()
            CharactersList(charactersList,innerPadding,onNavigate)
        }
        else -> Unit
    }
}
