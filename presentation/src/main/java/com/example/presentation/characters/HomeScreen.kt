package com.example.presentation.characters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.presentation.R
import com.example.presentation.characters.HomeScreenValues.CHARACTER_LIST_DEFAULT_SIZE
import com.example.presentation.characters.HomeScreenValues.HOME_SCREEN_GRID_CELLS
import com.example.presentation.uistate.UiState
import com.example.presentation.uistate.UiState.Error
import com.example.presentation.uistate.UiState.Loading
import com.example.presentation.uistate.UiState.Success
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
    val uistate by remember { allCharacters }.collectAsState()
    Column(modifier = modifier) {
        when (uistate) {
            is Error -> {
                Text(text = (uistate as Error).exception.message.toString())
            }

            is Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(dimensionResource(R.dimen.progress_bar_size)))
            }

            is Success<PagingData<GetCharactersQuery.Result>> -> {
                val charactersList =
                    remember {
                        flowOf(
                            (uistate as Success<PagingData<GetCharactersQuery.Result>>).data
                        )
                    }.collectAsLazyPagingItems()
                CharactersList(
                    charactersList = charactersList,
                    onNavigateToCharacterDetails = onNavigateToCharacterDetails
                )
            }

            else -> Unit
        }
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
        AsyncImage(
            model = item?.character?.image,
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
        columns = GridCells.Fixed(HOME_SCREEN_GRID_CELLS),
        modifier = modifier
            .fillMaxSize()
            .padding(),
        contentPadding = PaddingValues(dimensionResource(R.dimen.character_list_grid_padding)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.character_list_grid_spacing)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.character_list_grid_spacing)),
    ) {
        items(charactersList?.itemCount ?: CHARACTER_LIST_DEFAULT_SIZE) { index ->
            val item = charactersList?.get(index)
            CharactersListItem(
                item = item,
                onNavigateToCharacterDetails = onNavigateToCharacterDetails,

            )
        }
    }
}

private object HomeScreenValues {
    const val HOME_SCREEN_GRID_CELLS = 2
    const val CHARACTER_LIST_DEFAULT_SIZE = 0
}
