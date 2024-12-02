package com.example.domain.mapper


data class EpisodeDetails(
    val id: String = "",
    val airDate: String = "",
    val title: String? = "",
    val characters: List<Character> = emptyList()
)
