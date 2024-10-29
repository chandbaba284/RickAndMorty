package com.example.rickandmorty

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rickandmorty.presentation.characters.AllCharacters
import com.example.rickandmorty.utills.NavigationRoutes

@Composable
fun NavigationController(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController,
        NavigationRoutes.AllCharacters.name,
        modifier = modifier,
    ) {
        composable(NavigationRoutes.AllCharacters.name) {
            AllCharacters()
        }
        composable(NavigationRoutes.CharacterDetails.name) {
        }
    }
}
