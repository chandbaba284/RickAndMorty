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
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.common.module.DataState
import com.example.domain.mapper.Character
import com.example.domain.mapper.EpisodeDetails
import com.example.presentation.R
import com.example.presentation.dimens
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
                    CircularProgressIndicator(modifier = Modifier.size(MaterialTheme.dimens.progressBarSize))
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
            modifier = Modifier.padding(top = MaterialTheme.dimens.mediumLineHeight)
        )
        Text(
            text = episodeInfo.title.orEmpty(),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = MaterialTheme.dimens.mediumLineHeight)
        )
        Spacer(modifier = Modifier.padding(top = MaterialTheme.dimens.mediumLineHeight))
    }
}

@Composable
private fun EpisodeAirDate(episodeInfo: EpisodeDetails, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.air_date),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = MaterialTheme.dimens.mediumLineHeight)
        )
        Text(
            text = episodeInfo.airDate.orEmpty(),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = MaterialTheme.dimens.mediumLineHeight)
        )
        Spacer(modifier = Modifier.padding(top = MaterialTheme.dimens.mediumLineHeight ))
    }
}

@Composable
private fun CharactersList(characters: List<Character>?, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.characters),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = MaterialTheme.dimens.mediumLineHeight)
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
        contentPadding = PaddingValues(MaterialTheme.dimens.mediumLineHeight),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.mediumLineHeight),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.mediumLineHeight)
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
