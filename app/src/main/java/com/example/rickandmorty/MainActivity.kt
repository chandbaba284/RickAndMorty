package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.rickandmorty.presentation.CharactersViewModel
import com.example.rickandmorty.presentation.HomesScreen
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject lateinit var viewModel: CharactersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as RickAndMortyApplication).appComponent.inject(this)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                HomesScreen()
            }
        }
    }
}
