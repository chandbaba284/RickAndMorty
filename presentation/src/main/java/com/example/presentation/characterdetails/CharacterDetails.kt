package com.example.presentation.characterdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.common.module.DataState
import com.example.domain.mapper.CharacterDetails
import com.example.domain.mapper.Episode
import com.example.presentation.R
import com.example.presentation.characterdetails.CharacterDetailsValues.EPISODE_LIST_DEFAULT_SIZE
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CharacterDetails(
    characterDetails: StateFlow<DataState<CharacterDetails>>,
    modifier: Modifier = Modifier
) {
    val uiState = characterDetails.collectAsState().value
    Column(
        modifier = modifier
    ) {
        when (uiState) {
            is DataState.Error -> {
                val message = stringResource(uiState.errorMessage)
                Text(text = message)
            }

            DataState.Loading -> {
                ProgressBar()
            }

            is DataState.Success -> {
                CharacterDetailItems(uiState.data)
            }
        }
    }
}

@Composable
private fun ProgressBar(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(), // Ensures the Column fills the entire screen
        verticalArrangement = Arrangement.Center, // Centers content vertically
        horizontalAlignment = Alignment.CenterHorizontally // Centers content horizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(dimensionResource(R.dimen.progress_bar_size)) // Sets the specific size
        )
    }
}

@Composable
private fun CharacterDetailItems(
    item: CharacterDetails,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = item.image,
            contentDescription = "Grid Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.character_details_image_height)),
        )
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.character_list_grid_spacing))) {
            Text(text = item.name, style = MaterialTheme.typography.headlineLarge)
            item.apply {
                CharacterStatus(this)
                CharacterSpecies(this)
                CharacterGender(this)
                CharacterOrigin(this)
                CharacterOriginDimension(this)
                CharacterLocation(this)
                CharacterLocationDimension(this)
                EpisodesList(this)
            }
        }
    }
}

@Composable
private fun CharacterStatus(item: CharacterDetails, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_status_padding)))
        Row {
            Text(
                text = stringResource(R.string.status),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.character_details_spacer)))
            CustomTextWithStyleMediumColorPrimary(text = item.status?.uppercase().orEmpty())
        }
    }
}

@Composable
private fun CharacterSpecies(item: CharacterDetails, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_spacer)))
        Row {
            Text(
                text = stringResource(R.string.species),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.character_details_spacer)))
            CustomTextWithStyleMediumColorPrimary(text = item.species?.uppercase().orEmpty())
        }
    }
}

@Composable
private fun CharacterGender(item: CharacterDetails, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_spacer)))
        Row {
            Text(
                text = stringResource(R.string.gender),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.character_details_spacer)))
            CustomTextWithStyleMediumColorPrimary(text = item.gender?.uppercase().orEmpty())
        }
    }
}

@Composable
private fun CharacterOrigin(item: CharacterDetails, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_origin_spacer)))
        Text(text = stringResource(R.string.origin), style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_spacer)))
        Row {
            Text(
                text = stringResource(R.string.origin_name),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.character_details_spacer)))
            CustomTextWithStyleMediumColorPrimary(text = item.originName?.uppercase().orEmpty())
        }
    }
}

@Composable
private fun CharacterOriginDimension(item: CharacterDetails, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row {
            Text(
                text = stringResource(R.string.origin_dimension),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.character_details_spacer)))
            CustomTextWithStyleMediumColorPrimary(
                text = item.originDimension?.uppercase().orEmpty()
            )
        }
    }
}

@Composable
private fun CharacterLocation(item: CharacterDetails, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_origin_spacer)))
        Text(text = stringResource(R.string.location), style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.padding(top = 5.dp))
        Row {
            Text(
                text = stringResource(R.string.location_name),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.character_details_spacer)))
            CustomTextWithStyleMediumColorPrimary(text = item.locationName?.uppercase().orEmpty())
        }
    }
}

@Composable
private fun CharacterLocationDimension(
    item: CharacterDetails,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row {
            Text(
                text = stringResource(R.string.location_dimension),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.character_details_spacer)))
            CustomTextWithStyleMediumColorPrimary(
                text = item.locationDimension?.uppercase().orEmpty()
            )
        }
    }
}

@Composable
private fun EpisodesList(item: CharacterDetails, modifier: Modifier = Modifier) {
    LazyRow(
        contentPadding = PaddingValues(dimensionResource(R.dimen.character_list_item_padding)),
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.character_list_grid_spacing)
        ),
        modifier = modifier
    ) {
        items(item.episodes.size ?: EPISODE_LIST_DEFAULT_SIZE) { index ->
            val episode = item.episodes.get(index)
            EpisodeListItem(episode, index)
        }
    }
}

@Composable
private fun EpisodeListItem(item: Episode, index: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(dimensionResource(R.dimen.episode_item_size))
            .background(color = colorResource(R.color.episode_background_color))
    ) {
        Column(
            modifier = Modifier.matchParentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val episodeNumber = remember { mutableStateOf("EP $index") }
            Text(
                text = episodeNumber.value,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = item.season.orEmpty(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun CustomTextWithStyleMediumColorPrimary(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Text(
        text = text.uppercase(),
        style = style,
        color = color,
        modifier = modifier
    )
}

private object CharacterDetailsValues {
    const val EPISODE_LIST_DEFAULT_SIZE = 0
}
