package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.common.utills.NavigationRoutes
import com.example.presentation.HomeScreen
import com.example.presentation.R
import com.example.presentation.characterdetails.CharacterDetails
import com.example.presentation.viewmodel.CharacterDetailsViewModel
import com.example.presentation.viewmodel.CharactersViewModel
import com.example.presentation.viewmodel.HomeViewModel

@Composable
fun NavigationController(
    navController: NavHostController,
    viewModelFactory: ViewModelProvider.Factory,
) {
    val homeViewModel: HomeViewModel = viewModel(factory = viewModelFactory)
    val topBarTitle by homeViewModel.topBarTitle.collectAsState()
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.AllCharacters.route
        ) {
            composable(NavigationRoutes.AllCharacters.route) {
                val viewModel : CharactersViewModel = viewModel(factory = viewModelFactory)
                homeViewModel.updateAppBarTitle(stringResource(R.string.app_name))
                HomeScreen(viewModel.charactersState,topBarTitle)
            }
            composable(
                route = NavigationRoutes.CharacterDetails.route,
                arguments = listOf(navArgument("CharacterId") { type = NavType.StringType })
            ) { navBackStackEntry ->
                val characterId = navBackStackEntry.arguments?.getString("CharacterId")
                val viewModel: CharacterDetailsViewModel = viewModel(factory = viewModelFactory)
                characterId.orEmpty().let { viewModel.getCharacterDetails(it) }
                homeViewModel.updateAppBarTitle(viewModel.getCharacterName())
                CharacterDetails(viewModel.characterDetails,topBarTitle)

            }
        }
}

