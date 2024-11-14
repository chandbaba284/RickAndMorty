package com.example.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.PagingData
import com.example.presentation.characters.AllCharacters
import com.example.presentation.uistate.UiState
import com.exmple.rickandmorty.GetCharactersQuery
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    allCharacters: StateFlow<UiState<PagingData<GetCharactersQuery.Result>>>,
    topBarTitle: String,
    onNavigateToCharacterDetails: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { RickAndMortyAppBar(topBarTitle) },
        content = { innerPadding ->
            AllCharacters(allCharacters = allCharacters,Modifier.padding(innerPadding), onNavigateToCharacterDetails = onNavigateToCharacterDetails)
        },
    )
}
