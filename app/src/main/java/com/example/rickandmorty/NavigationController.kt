package com.example.rickandmorty

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rickandmorty.presentation.characters.AllCharacters
import com.example.rickandmorty.presentation.characters.CharacterDetailsScreen
import com.example.rickandmorty.utills.NavigationRoutes

@Composable
fun NavigationController(navController: NavHostController,paddingValues: PaddingValues)  {
    NavHost(navController,NavigationRoutes.AllCharacters.name,
        modifier = Modifier.padding(paddingValues)) {
        composable(NavigationRoutes.AllCharacters.name){
            AllCharacters()
        }
        composable(NavigationRoutes.CharacterDetails.name){
            CharacterDetailsScreen()
        }
    }
}