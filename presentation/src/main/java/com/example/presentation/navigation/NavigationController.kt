package com.example.presentation.navigation

import CharacterDetails
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.presentation.R
import com.example.presentation.characters.HomeScreen
import com.example.presentation.navigation.routes.RouteCharacterDetails
import com.example.presentation.navigation.routes.RouteHome
import com.example.presentation.viewmodel.CharacterDetailsViewModel
import com.example.presentation.viewmodel.CharactersViewModel

@Composable
fun NavigationController(
    viewModelFactory: ViewModelProvider.Factory,
) {
    val topBarTitle = remember { mutableStateOf("") }
    val navController = rememberNavController()
    val onNavigateCharacterDetails: (String) -> Unit =
        { characterId: String -> navController.navigate(RouteCharacterDetails(characterId = characterId)) }

    NavHost(
        navController = navController,
        startDestination = RouteHome
    ) {
        composable<RouteHome> {
            val viewModel: CharactersViewModel = viewModel(factory = viewModelFactory)
            topBarTitle.value = stringResource(R.string.app_name)
            HomeScreen(
                allCharacters = viewModel.charactersState,
                topBarTitle = topBarTitle.value,
                onNavigateToCharacterDetails = onNavigateCharacterDetails
            )
        }
        composable<RouteCharacterDetails> { navBackStackEntry ->
            val characterDetails: RouteCharacterDetails = navBackStackEntry.toRoute()
            val viewModel: CharacterDetailsViewModel = viewModel(factory = viewModelFactory)
            viewModel.getCharacterDetails(characterDetails.characterId)
            topBarTitle.value = viewModel.getCharacterName()
            CharacterDetails(
                characterDetails = viewModel.characterDetails,
                topBarTitle = topBarTitle.value
            )
        }
    }
}
