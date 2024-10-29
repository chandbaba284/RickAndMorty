package com.example.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.exmple.rickandmorty.GetCharactersQuery
import com.exmple.rickandmorty.fragment.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharactersTestRepository : CharactersRepository {
    override suspend fun getCharacters(): Flow<PagingData<GetCharactersQuery.Result>> {
        val items =
            listOf(
                GetCharactersQuery.Result(
                    "",
                    Character(
                        name = "",
                        "",
                        "",
                        Character.Location("", ""),
                    ),
                ),
                GetCharactersQuery.Result(
                    "",
                    Character(
                        name = "",
                        "",
                        "",
                        Character.Location("", ""),
                    ),
                ),
            )

        return flow { PagingData.from(items) }
    }
}
