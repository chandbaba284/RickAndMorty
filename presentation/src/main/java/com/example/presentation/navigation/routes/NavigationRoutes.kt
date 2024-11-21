package com.example.presentation.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
object RouteHome

@Serializable
data class RouteCharacterDetails(val characterId: String)
