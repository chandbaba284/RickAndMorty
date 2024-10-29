package com.example.rickandmorty.presentation.characters

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.RickAndMortyApplication
import com.example.rickandmorty.presentation.CharactersViewModel
import com.example.rickandmorty.presentation.uistate.UiState

@Composable
fun AllCharacters() {
    val context = LocalContext.current

    val activity =
        context as? ComponentActivity
            ?: throw IllegalStateException("ParentComposable must be called from an Activity")

    val appComponent = (activity.application as RickAndMortyApplication).appComponent
    val viewModelFactory = appComponent.viewModelFactory() // Get the ViewModelFactory

    val viewModel: CharactersViewModel =
        remember {
            ViewModelProvider(activity, viewModelFactory).get(CharactersViewModel::class.java)
        }
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
