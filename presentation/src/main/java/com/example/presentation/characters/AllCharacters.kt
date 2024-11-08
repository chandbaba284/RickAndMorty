package com.example.presentation.characters

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.presentation.R
import com.example.presentation.viewmodel.CharactersViewModel
import com.example.presentation.uistate.UiState.Empty
import com.example.presentation.uistate.UiState.Success
import com.example.presentation.uistate.UiState.Error
import com.example.presentation.uistate.UiState.Loading
import com.exmple.rickandmorty.GetCharactersQuery
import kotlinx.coroutines.flow.flowOf

@Composable
fun AllCharacters(viewModel: CharactersViewModel, navController: NavHostController) {

    LaunchedEffect(true) {
        viewModel.fetchData()
    }
    val uistate = viewModel.charactersState.collectAsState().value
    when (uistate) {
        is Empty -> {}
        is Error -> {}
        is Loading -> {
            CircularProgressIndicator(modifier = Modifier.size(dimensionResource(R.dimen.progress_bar_size)))
        }
        is Success<PagingData<GetCharactersQuery.Result>> -> {
            val charactersList = remember { flowOf(uistate.data) }.collectAsLazyPagingItems()
            CharactersList(charactersList,navController)
        }
        else -> Unit
    }
}
