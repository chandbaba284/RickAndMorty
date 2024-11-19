package com.example.domain.mapper

import com.exmple.rickandmorty.GetCharacterDetailsByIdQuery
import com.exmple.rickandmorty.GetEpisodeDetailsByIdQuery

fun GetCharacterDetailsByIdQuery.Character.toCharacterDetailsMapper(): CharacterDetailsMapper {
    val character = this.character
    val episodes = character.episode
    return CharacterDetailsMapper(
        id = character.id,
        name = character.name,
        image = character.image,
        status = character.status,
        species = character.species,
        gender = character.gender,
        originName = character.origin?.name,
        originDimension = character.origin?.dimension,
        locationName = character.location?.location?.name,
        locationDimension = character.location?.location?.dimension, episodes = episodes
    )
}

fun GetEpisodeDetailsByIdQuery.Data.toEpisodeDetailsMapper() : EpisodeDetailsMapper{
    val episodeDetails = this.episode
    val characters = episodeDetails?.characters
    return EpisodeDetailsMapper(
        id = episodeDetails?.id,
        airDate = episodeDetails?.air_date,
        title = episodeDetails?.name,
        characters = characters.toAllCharactersList()
    )
}

fun List<GetEpisodeDetailsByIdQuery.Character?>?.toAllCharactersList(): List<Character> {
    return this?.mapNotNull { character ->
        character?.let {
            Character(
                characterId = it. id?: "",
                characterName = it.name ?: "Unknown",
                image = it.image?:""

            )
        }
    } ?: emptyList()
}