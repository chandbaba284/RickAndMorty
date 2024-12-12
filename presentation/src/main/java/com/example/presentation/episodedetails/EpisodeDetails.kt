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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.common.module.DataState
import com.example.domain.mapper.Character
import com.example.domain.mapper.EpisodeDetails
import com.example.presentation.R
import com.example.presentation.episodedetails.EpisodeDetailsValues.CHARACTERS_GRID_CELL_COUNT
import com.example.presentation.episodedetails.EpisodeDetailsValues.EPISODE_LIST_DEFAULT_SIZE
import kotlinx.coroutines.flow.StateFlow

@Composable
fun EpisodeDetails(
    episodeDetails: StateFlow<DataState<EpisodeDetails>>,
    modifier: Modifier = Modifier
) {
    val episodeInfo = episodeDetails.collectAsState().value
    Column(
        modifier = modifier
    ) {
        when (episodeInfo) {
            is DataState.Error -> {
                Text(text = episodeInfo.exception.message.toString())
            }

            DataState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(dimensionResource(R.dimen.progress_bar_size)))
                }
            }

            is DataState.Success -> {
                EpisodeScreen(episodeInfo.data)
            }
        }
    }
}

@Composable
private fun EpisodeScreen(episodeDetails: EpisodeDetails, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        episodeDetails.apply {
            EpisodeName(this)
            EpisodeAirDate(this)
            CharactersList(this.characters)
        }
    }
}

@Composable
private fun EpisodeName(episodeInfo: EpisodeDetails, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.episode_name),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.episode_details_topbar_padding))
        )
        Text(
            text = episodeInfo.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.episode_details_spacing_between_title_value))
        )
        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_status_padding)))
    }
}

@Composable
private fun EpisodeAirDate(episodeInfo: EpisodeDetails, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.air_date),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.episode_details_topbar_padding))
        )
        Text(
            text = episodeInfo.airDate.ifEmpty { stringResource(R.string.unavailable) },
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.episode_details_spacing_between_title_value))
        )
        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_status_padding)))
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
        columns = GridCells.Fixed(CHARACTERS_GRID_CELL_COUNT),
        modifier = modifier
            .fillMaxSize()
            .padding(),
        contentPadding = PaddingValues(dimensionResource(R.dimen.character_list_grid_padding)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.character_list_grid_spacing)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.character_list_grid_spacing))
    ) {
        items(characters?.size ?: EPISODE_LIST_DEFAULT_SIZE) { index ->
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

private object EpisodeDetailsValues {
    const val EPISODE_LIST_DEFAULT_SIZE = 0
    const val CHARACTERS_GRID_CELL_COUNT = 2
}
