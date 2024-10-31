package com.example.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.exmple.rickandmorty.GetCharactersQuery
import com.exmple.rickandmorty.fragment.Character
import com.exmple.rickandmorty.fragment.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import repository.CharactersRepository

class CharactersTestRepository : CharactersRepository {
    override suspend fun getCharacters(): Flow<PagingData<GetCharactersQuery.Result>> {
        val items =
            listOf(
                GetCharactersQuery.Result(
                    "",
                    Character(
                        name = "",
                        "",
                        "","","","",
                        location = Character.Location("", Location("","",""))
                    ),
                ),
                GetCharactersQuery.Result(
                    "",
                    Character(
                        name = "",
                        "",
                        "","","","",
                        Character.Location("", Location("","",""),
                    ),
                ),
            ))

        return flow { PagingData.from(items) }
    }
}
