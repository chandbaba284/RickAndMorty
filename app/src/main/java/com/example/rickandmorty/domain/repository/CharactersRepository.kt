package com.example.rickandmorty.domain.repository

import androidx.paging.Pager
import com.exmple.rickandmorty.GetCharactersQuery

interface CharactersRepository {
    suspend fun getCharacters() : Pager<Int, GetCharactersQuery.Result>
}