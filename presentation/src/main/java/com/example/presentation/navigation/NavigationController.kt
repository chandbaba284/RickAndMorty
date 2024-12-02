package com.example.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.presentation.R
import com.example.presentation.characterdetails.CharacterDetails
import com.example.presentation.characters.HomeScreen
import com.example.presentation.episodedetails.EpisodeDetails
import com.example.presentation.navigation.routes.RouteCharacterDetails
import com.example.presentation.navigation.routes.RouteEpisodeDetails
import com.example.presentation.navigation.routes.RouteHome
import com.example.presentation.viewmodel.CharacterDetailsViewModel
import com.example.presentation.viewmodel.CharactersViewModel
import com.example.presentation.viewmodel.EpisodeDetailsViewModel

@Composable
fun NavigationController(
    onTopBarTitleChange: (String) -> Unit,
    viewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val onNavigateCharacterDetails: (String) -> Unit =
        { characterId: String -> navController.navigate(RouteCharacterDetails(characterId = characterId)) }

    NavHost(
        navController = navController,
        startDestination = RouteHome,
        modifier = modifier.padding(),
    ) {
        composable<RouteHome> {
            val viewModel: CharactersViewModel = viewModel(factory = viewModelFactory)
            topBarTitle.value = stringResource(R.string.app_name)
            val onNavigateCharacterDetails: (String) -> Unit = { characterId: String -> navController.navigate(RouteCharacterDetails(characterId = characterId)) }
            HomeScreen(allCharacters = viewModel.charactersState, topBarTitle = topBarTitle.value, onNavigateToCharacterDetails = onNavigateCharacterDetails)
        }
        composable<RouteCharacterDetails> { navBackStackEntry ->
            val characterDetails: RouteCharacterDetails = navBackStackEntry.toRoute()
            val viewModel: CharacterDetailsViewModel = viewModel(factory = viewModelFactory)
            viewModel.getCharacterDetails(characterDetails.characterId)
            onTopBarTitleChange(viewModel.getCharacterName())
            CharacterDetails(characterDetails = viewModel.characterDetails)
            topBarTitle.value = viewModel.getCharacterName()
            val onNavigateToEpisodeDetails : (String) -> Unit = {episodeId : String -> navController.navigate(RouteEpisodeDetails(episodeId = episodeId))}
            CharacterDetails(characterDetails = viewModel.characterDetails, topBarTitle = topBarTitle.value,onNavigateToEpisodeDetails)

        }
        composable<RouteEpisodeDetails> { navBackStackEntry ->
          val episodeDetails : RouteEpisodeDetails = navBackStackEntry.toRoute()
          val viewModel : EpisodeDetailsViewModel = viewModel(factory = viewModelFactory)
          val episodeId = episodeDetails.episodeId
          viewModel.getEpisodeDetailsByUsingEpisodeId(episodeId)
          topBarTitle.value = viewModel.getEpisodeTitle()
           EpisodeDetails(topBarTitle = topBarTitle.value,episodeDetails = viewModel.episodeDetails)

        }
    }
}
