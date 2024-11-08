package com.example.presentation.characterdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.domain.mapper.CharacterDetailsMapper
import com.example.presentation.R
import com.example.presentation.uistate.UiState
import com.example.presentation.viewmodel.CharacterDetailsViewModel
import com.exmple.rickandmorty.fragment.Character

@Composable
fun CharacterDetails(
    navController: NavHostController,
    charactersViewModel: CharacterDetailsViewModel,
    characterId: String
) {

    LaunchedEffect(characterId) {
        charactersViewModel.getCharacterDetails(characterId)
    }
    val uiState = charactersViewModel.characterDetails.collectAsState().value
    when (uiState) {
        UiState.Empty -> {}
        is UiState.Error -> {}
        UiState.Loading -> {}
        is UiState.Success -> {
            CharacterDetailItems(uiState.data)

        }
    }
}

@Composable
fun CharacterDetailItems(item: CharacterDetailsMapper) {
    val painter = rememberAsyncImagePainter(item.image)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Image(
            painter = painter,
            contentDescription = "Grid Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.character_details_image_height)),
        )
        Column(modifier = Modifier.padding(start = dimensionResource(R.dimen.character_list_grid_spacing))) {
            Text(text = item.name ?: "", style = MaterialTheme.typography.headlineLarge)
            CharacterStatus(item)
        }


    }
}

@Composable
fun CharacterStatus(item: CharacterDetailsMapper) {
    Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_status_padding)))
    Row {
        Text(
            text = stringResource(R.string.status),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.character_details_spacer)))
        Text(
            text = item.status?.uppercase() ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
    CharacterSpecies(item)

}

@Composable
fun CharacterSpecies(item: CharacterDetailsMapper) {
    Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_spacer)))

    Row {
        Text(
            text = stringResource(R.string.species),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.character_details_spacer)))
        Text(
            text = item.species?.uppercase() ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
    CharacterGender(item)

}

@Composable
fun CharacterGender(item: CharacterDetailsMapper) {
    Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_spacer)))

    Row {
        Text(
            text = stringResource(R.string.gender),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.character_details_spacer)))
        Text(
            text = item.gender?.uppercase() ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

    }
    CharacterOrigin(item)
}

@Composable
fun CharacterOrigin(item: CharacterDetailsMapper) {
    Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_origin_spacer)))
    Text(text = stringResource(R.string.origin), style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_spacer)))
    Row {
        Text(
            text = stringResource(R.string.origin_name),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.character_details_spacer)))
        Text(
            text = item.originName?.uppercase() ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

    }
    CharacterOriginDimension(item)

}

@Composable
fun CharacterOriginDimension(item: CharacterDetailsMapper) {
    Row {
        Text(
            text = stringResource(R.string.origin_dimension),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.character_details_spacer)))
        Text(
            text = item.originDimension?.uppercase() ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
    CharacterLocation(item)
}

@Composable
fun CharacterLocation(item: CharacterDetailsMapper) {
    Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.character_details_origin_spacer)))
    Text(text = stringResource(R.string.location), style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.padding(top = 5.dp))
    Row {
        Text(
            text = stringResource(R.string.location_name),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.character_details_spacer)))
        Text(
            text = item.locationName?.uppercase() ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

    }
    CharacterLocationDimension(item)
}

@Composable
fun CharacterLocationDimension(item: CharacterDetailsMapper) {
    Row {
        Text(
            text = stringResource(R.string.location_dimension),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.character_details_spacer)))
        Text(
            text = item.locationDimension?.uppercase() ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
    EpisodesList(item)
}

@Composable
fun EpisodesList(item: CharacterDetailsMapper) {
    LazyRow(contentPadding = PaddingValues(dimensionResource(R.dimen.character_list_item_padding)),horizontalArrangement = Arrangement.spacedBy(
        dimensionResource(R.dimen.character_list_grid_spacing)
    )) {
        items(item.episodes?.size ?: 0) { index ->
            val episode = item.episodes?.get(index)
            EpisodeListItem(episode, index)
        }
    }

}

@Composable
fun EpisodeListItem(item: Character.Episode?, index: Int) {
    Box(
        modifier = Modifier
            .height(dimensionResource(R.dimen.episode_item_size))
            .width(dimensionResource(R.dimen.episode_item_size))
            .background(color = colorResource(R.color.episode_background_color))
    ) {
        Column(
            modifier = Modifier.matchParentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val episodeNumber = "EP $index"
            Text(
                text = episodeNumber,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = item?.episode ?: "",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}







