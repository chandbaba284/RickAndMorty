package com.example.rickandmorty.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.NavigationController
import com.example.rickandmorty.R

@Composable
fun HomesScreen() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { RickAndMortyAppBar(stringResource(R.string.app_name)) },
        content = {innerPadding->
            NavigationController(navController,innerPadding)
        }
    )
}