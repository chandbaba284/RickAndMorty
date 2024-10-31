package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.common.utills.NavigationRoutes
import com.example.presentation.viewmodel.CharactersViewModel
import com.example.presentation.characters.AllCharacters

@Composable
fun NavigationController(
    navController: NavHostController,
    modifier: Modifier,
    viewModel: CharactersViewModel,
) {
    NavHost(
        navController,
        NavigationRoutes.AllCharacters.name,
        modifier = modifier,
    ) {
        composable(NavigationRoutes.AllCharacters.name) {
            AllCharacters(viewModel)
        }
        composable(NavigationRoutes.CharacterDetails.name) {
        }
    }
}
