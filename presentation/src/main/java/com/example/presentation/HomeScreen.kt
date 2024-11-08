package com.example.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.presentation.navigation.NavigationController

@Composable
fun HomesScreen(viewModelFactory: ViewModelProvider.Factory) {
    val navController = rememberNavController()
    var topBarTitle by remember { mutableStateOf("") }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { RickAndMortyAppBar(topBarTitle) },
        content = { innerPadding ->
            NavigationController(onTitleChange = {newTitle -> topBarTitle = newTitle},navController, Modifier.padding(innerPadding),viewModelFactory)
        },
    )
}
