package com.example.presentation.navigation

import androidx.navigation.NavHostController

class NavControllerNavigator(private val navController: NavHostController?=null) : Navigator {
    override fun navigateTo(route: String) {
        navController?.navigate(route)
    }

    override fun popBackStack() {
        navController?.popBackStack()
    }
}