package com.example.rickandmorty.presentation.characters

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.presentation.CharactersViewModel

@Composable
fun AllCharacters(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<CharactersViewModel>()
    val uistate = viewModel.charactersState.collectAsState()
    if (uistate.value.isLoading == true) {
        CircularProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
    val characters = uistate.value.data?.flow?.collectAsLazyPagingItems()
    CharactersList(characters)


}