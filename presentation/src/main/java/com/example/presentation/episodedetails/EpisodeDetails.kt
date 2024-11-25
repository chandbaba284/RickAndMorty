package com.example.presentation.episodedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.domain.mapper.Character
import com.example.domain.mapper.EpisodeDetailsMapper
import com.example.presentation.R
import com.example.presentation.RickAndMortyAppBar
import com.example.presentation.uistate.UiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun EpisodeDetails(
    topBarTitle: String,
    episodeDetails: StateFlow<UiState<EpisodeDetailsMapper>>,
    modifier: Modifier = Modifier
) {
    val episodeInfo = episodeDetails.collectAsState().value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { RickAndMortyAppBar(topBarTitle) },
        content = { innerPadding ->
            when (episodeInfo) {
                is UiState.Error -> {
                    Text(text = episodeInfo.exception.message.toString())
                }

                UiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(dimensionResource(R.dimen.progress_bar_size)))
                    }
                }

                is UiState.Success -> {
                    EpisodeName(episodeInfo.data, modifier.padding(innerPadding))

                }
            }
        },
    )
}

@Composable
private fun EpisodeName(episodeInfo: EpisodeDetailsMapper, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.episode_name),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.episode_details_topbar_padding))
        )
        Text(
            text = episodeInfo.title.orEmpty(),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.episode_details_spacing_between_title_value))
        )
        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_status_padding)))
        EpisodeAirDate(episodeInfo)
    }
}

@Composable
private fun EpisodeAirDate(episodeInfo: EpisodeDetailsMapper, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.air_date),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.episode_details_topbar_padding))
        )
        Text(
            text = episodeInfo.airDate.orEmpty(),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.episode_details_spacing_between_title_value))
        )
        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_status_padding)))
        CharactersList(episodeInfo.characters)
    }
}

@Composable
private fun CharactersList(characters: List<Character>?, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.characters),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.episode_details_topbar_padding))
        )
        CharactersListItem(characters)

    }
}

@Composable
private fun CharactersListItem(characters: List<Character>?, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(),
        contentPadding = PaddingValues(dimensionResource(R.dimen.character_list_grid_padding)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.character_list_grid_spacing)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.character_list_grid_spacing))
    ) {
        items(characters?.size ?: 0) { index ->
            val item = characters?.get(index)
            AsyncImage(
                model = item?.image,
                contentDescription = "test",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }


    }

}
