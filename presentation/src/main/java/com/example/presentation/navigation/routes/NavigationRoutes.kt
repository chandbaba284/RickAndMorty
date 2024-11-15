package com.example.presentation.navigation.routes

import kotlinx.serialization.Serializable


@Serializable
object  HomeScreen

@Serializable
data class  CharacterDetailsScreen(val characterId : String)
