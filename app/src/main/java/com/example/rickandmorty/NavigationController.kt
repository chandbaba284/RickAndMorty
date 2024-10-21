package com.example.rickandmorty

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rickandmorty.presentation.characters.AllCharacters
@Composable
fun NavigationController(navController: NavHostController,paddingValues: PaddingValues)  {
    NavHost(navController,"Home", modifier = Modifier.padding(paddingValues)) {
        composable("Home"){
            AllCharacters()
        }
    }
}