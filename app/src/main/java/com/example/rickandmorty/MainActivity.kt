package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.rickandmorty.presentation.RickAndMortyAppBar
import com.example.rickandmorty.presentation.characters.AllCharacters
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { RickAndMortyAppBar(this.getString(R.string.app_name)) }) { innerPadding ->

                    AllCharacters(Modifier.padding(innerPadding))
                }
            }
        }
    }
}