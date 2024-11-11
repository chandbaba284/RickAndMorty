package com.example.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.exmple.rickandmorty.GetCharactersQuery
import com.exmple.rickandmorty.fragment.Character
import com.exmple.rickandmorty.fragment.Location

class TestCharactersRepository {
    fun getCharacters(): PagingData<GetCharactersQuery.Result> {
        val items =
            listOf(
                GetCharactersQuery.Result(
                    "",
                    Character(
                        name = "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        location = Character.Location("", Location("", "", "")),
                    ),
                ),
                GetCharactersQuery.Result(
                    "",
                    Character(
                        name = "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        Character.Location(
                            "",
                            Location("", "", ""),
                        ),
                    ),
                ),
            )

        return PagingData.from(items)
    }
}
