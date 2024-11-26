package com.example.presentation.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.common.module.DataState
import com.example.common.module.DataState.Error
import com.example.common.module.DataState.Loading
import com.example.common.module.DataState.Success
import com.example.presentation.R
import com.example.presentation.RickAndMortyAppBar
import com.exmple.rickandmorty.GetCharactersQuery
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    allCharacters: StateFlow<DataState<PagingData<GetCharactersQuery.Result>>>,
    topBarTitle: String,
    onNavigateToCharacterDetails: (String) -> Unit
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { RickAndMortyAppBar(topBarTitle) },
        content = { innerPadding ->
            AllCharacters(allCharacters = allCharacters,modifier = modifier.padding(innerPadding), onNavigateToCharacterDetails = onNavigateToCharacterDetails)
        },
    )
}


@Composable
private fun AllCharacters(
    onNavigateToCharacterDetails: (String) -> Unit,
    allCharacters: StateFlow<DataState<PagingData<GetCharactersQuery.Result>>>,
    modifier: Modifier = Modifier,
    onNavigateToCharacterDetails: (String) -> Unit
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
            CharactersList(
                charactersList = charactersList,
                modifier = modifier.padding(),
                onNavigateToCharacterDetails = onNavigateToCharacterDetails
            )
        }

        else -> Unit
    }
}

@Composable
private fun CharactersListItem(
    onNavigateToCharacterDetails: (String) -> Unit,
    item: GetCharactersQuery.Result?,
    modifier: Modifier = Modifier,

) {
    Box(
        modifier =
        modifier
            .fillMaxWidth()
            .aspectRatio(AspectRatios.AspectRatio_List_Item)
            .clickable {
                onNavigateToCharacterDetails(item?.character?.id ?: "")
            }
    ) {
        val painter = rememberAsyncImagePainter(item?.character?.image)
        Image(
            painter = painter,
            contentDescription = "Grid Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Text(
            text = item?.character?.name ?: "",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier =
            Modifier
                .align(Alignment.BottomCenter)
                .padding(dimensionResource(R.dimen.character_list_item_padding)),
        )
    }
}


@Composable
private fun CharactersList(
    onNavigateToCharacterDetails:
    (String) -> Unit,
    charactersList:
    LazyPagingItems<GetCharactersQuery.Result>?,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(),
        contentPadding = PaddingValues(dimensionResource(R.dimen.character_list_grid_padding)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.character_list_grid_spacing)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.character_list_grid_spacing)),
    ) {
        items(charactersList?.itemCount ?: 0) { index ->
            val item = charactersList?.get(index)
            CharactersListItem(
                item = item,
                onNavigateToCharacterDetails = onNavigateToCharacterDetails,

            )
        }
    }
}
