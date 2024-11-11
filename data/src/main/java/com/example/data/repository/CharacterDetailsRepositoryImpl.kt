package com.example.data.repository

import com.apollographql.apollo.ApolloClient
import com.example.domain.mapper.CharacterDetails
import com.example.domain.repository.CharacterDetailsRepository
import com.exmple.rickandmorty.GetCharacterDetailsByIdQuery

class CharacterDetailsRepositoryImpl(val apolloClient: ApolloClient) : CharacterDetailsRepository {
    override suspend fun getCharacterDetailsById(id: String): CharacterDetails {
        val response = apolloClient.query(GetCharacterDetailsByIdQuery(id)).execute()
        if (!response.hasErrors()){
            response.data?.character?.character?.let {
                return CharacterDetails(id = it.id?:"", name = it.name?:"", image = it.image?:"", status = it.status?:"", species = it.species?:"", gender = it.gender?:"")

            }
        }
        return CharacterDetails()
    }
}