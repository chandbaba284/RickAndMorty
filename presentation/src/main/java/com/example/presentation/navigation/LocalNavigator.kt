package com.example.presentation.navigation

import androidx.compose.runtime.compositionLocalOf

val LocalNavigator = compositionLocalOf<Navigator> {
    error("Navigator not provided")
}