package com.example.presentation.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.paging.compose.LazyPagingItems
import com.example.presentation.R
import com.exmple.rickandmorty.GetCharactersQuery

@Composable
fun CharactersList(
    charactersList: LazyPagingItems<GetCharactersQuery.Result>?,
    innerPadding: PaddingValues,
    onNavigate: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().padding(innerPadding),
        contentPadding = PaddingValues(dimensionResource(R.dimen.character_list_grid_padding)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.character_list_grid_spacing)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.character_list_grid_spacing)),
    ) {
        items(charactersList?.itemCount ?: 0) { index ->
            val item = charactersList?.get(index)
            CharactersListItem(onNavigate,item)
        }
    }
}
