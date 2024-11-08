package com.example.common.utills

sealed class NavigationRoutes(val route: String) {
    data object AllCharacters : NavigationRoutes("AllCharacters")
    data object CharacterDetails : NavigationRoutes("CharacterDetails/{CharacterId}") {
        fun createRoute(characterId: String) = "CharacterDetails/$characterId"
    }

}
