package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.presentation.RickAndMortyAppBar
import com.example.presentation.RickAndMortyTheme
import com.example.presentation.navigation.NavigationController
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as RickAndMortyApplication).appComponent.inject(this)
        enableEdgeToEdge()
        setContent {
            val topBarTitle = remember { mutableStateOf("") }
            RickAndMortyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize().systemBarsPadding(),
                    topBar = { RickAndMortyAppBar(topBarTitle.value) },
                    content = { innerPadding ->
                        NavigationController(
                            viewModelFactory = viewModelFactory,
                            modifier = Modifier.padding(innerPadding),
                            onTopBarTitleChange = { title -> topBarTitle.value = title }
                        )
                    }
                )
            }
        }
    }
}
