package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.presentation.navigation.LocalNavigator
import com.example.presentation.navigation.NavControllerNavigator
import com.example.presentation.navigation.NavigationController
import com.example.presentation.navigation.Navigator
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as RickAndMortyApplication).appComponent.inject(this)
        enableEdgeToEdge()

        setContent {
            RickAndMortyTheme {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .systemBarsPadding(),
                ) {
                    val navController = rememberNavController()
                    CompositionLocalProvider(
                        LocalNavigator provides
                            NavControllerNavigator(
                                navController,
                            ),
                    ) {
                        NavigationController(navController, viewModelFactory)
                    }
                }
            }
        }
    }
}
