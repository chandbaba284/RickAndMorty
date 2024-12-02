package com.example.domain.repository

import com.example.common.module.DataState
import com.example.domain.mapper.CharacterDetails

interface CharacterDetailsRepository {
    suspend fun getCharacterDetailsById(id : String) : DataState<CharacterDetails>
}