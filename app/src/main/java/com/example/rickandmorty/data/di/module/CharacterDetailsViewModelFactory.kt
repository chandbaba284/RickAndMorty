package com.example.rickandmorty.data.di.module

import com.example.presentation.viewmodel.CharacterDetailsViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface CharacterDetailsViewModelFactory {
    fun create(characterId : String) : CharacterDetailsViewModel
}