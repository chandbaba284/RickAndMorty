package com.example.presentation.characters

import AspectRatios
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.common.utills.NavigationRoutes
import com.example.presentation.R
import com.exmple.rickandmorty.GetCharactersQuery

@Composable
fun CharactersListItem(
    onNavigate : (String) -> Unit,
    item: GetCharactersQuery.Result?
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .aspectRatio(AspectRatios.AspectRatio_List_Item).clickable {
                    onNavigate(NavigationRoutes.CharacterDetails.createRoute(item?.character?.id?:""))
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
            text = item?.character?.name?:"", style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(dimensionResource(R.dimen.character_list_item_padding)),
        )
    }
}
