package com.example.domain.mapper

data class CharacterDetails(
    val id: String,
    val name: String,
    val image: String,
    val status: String? = null,
    val species: String? = null,
    val gender: String? = null,
    val originName: String,
    val originDimension: String? = null,
    val locationName: String,
    val locationDimension: String? = null,
    val episodes: List<Episode>
)
