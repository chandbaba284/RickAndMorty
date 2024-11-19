package com.example.domain.repository

import com.apollographql.apollo.api.ApolloResponse
import com.example.domain.mapper.CharacterDetailsMapper
import com.exmple.rickandmorty.GetCharacterDetailsByIdQuery

interface CharacterDetailsRepository {
    suspend fun getCharacterDetailsById(id : String) : Result<CharacterDetailsMapper>
}