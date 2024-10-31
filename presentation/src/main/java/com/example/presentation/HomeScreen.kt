package com.example.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.presentation.navigation.NavigationController
import com.example.presentation.viewmodel.CharactersViewModel

@Composable
fun HomesScreen(viewModel: CharactersViewModel) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { RickAndMortyAppBar(stringResource(R.string.app_name)) },
        content = { innerPadding ->
            NavigationController(navController, Modifier.padding(innerPadding),viewModel)
        },
    )
}
