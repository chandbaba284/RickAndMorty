package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.common.utills.NavigationRoutes
import com.example.presentation.R
import com.example.presentation.characterdetails.CharacterDetails
import com.example.presentation.characters.AllCharacters
import com.example.presentation.viewmodel.CharacterDetailsViewModel
import com.example.presentation.viewmodel.CharactersViewModel

@Composable
fun NavigationController(
    onTitleChange: (String) -> Unit,
    navController: NavHostController,
    modifier: Modifier,
    viewModelFactory: ViewModelProvider.Factory,
) {
    NavHost(
        navController,
        NavigationRoutes.AllCharacters.route,
        modifier = modifier,
    ) {
        composable(NavigationRoutes.AllCharacters.route) {
            val viewModel: CharactersViewModel = viewModel(factory = viewModelFactory)
            onTitleChange(stringResource(R.string.app_name))
            AllCharacters(viewModel, navController)
        }
        composable(
            route = NavigationRoutes.CharacterDetails.route,
            arguments = listOf(navArgument("CharacterId") { type = NavType.StringType })){navBackStackEntry ->
            val characterId = navBackStackEntry.arguments?.getString("CharacterId")
            val viewModel: CharacterDetailsViewModel = viewModel(factory = viewModelFactory)
            onTitleChange(viewModel.getCharacterName())
            CharacterDetails(navController,viewModel,characterId?:"")

        }
    }
}
