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
                return CharacterDetails(it.id?:"",it.name?:"",it.image?:"",it.status?:"",it.species?:"",it.gender?:"")

            }
        }
        return CharacterDetails()
    }
}