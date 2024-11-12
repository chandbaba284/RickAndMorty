package com.example.presentation.navigation

interface Navigator {
    fun navigateTo(route: String)
    fun popBackStack()

}