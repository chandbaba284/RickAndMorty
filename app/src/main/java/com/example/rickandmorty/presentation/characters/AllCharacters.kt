package com.example.rickandmorty.presentation.characters

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.RickAndMortyApplication

import com.example.rickandmorty.presentation.CharactersViewModel

@Composable
fun AllCharacters(modifier: Modifier = Modifier) {
    val context = LocalContext.current.applicationContext as RickAndMortyApplication
    val viewModelFactory = context.appComponent.viewModelFactory()
    val viewModel: CharactersViewModel = ViewModelProvider(LocalContext.current as ViewModelStoreOwner, viewModelFactory).get(CharactersViewModel::class.java)
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