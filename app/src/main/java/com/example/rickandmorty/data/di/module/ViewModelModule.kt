package com.example.rickandmorty.data.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.data.di.viewmodel.ViewModelFactoryProvider
import com.example.rickandmorty.presentation.CharactersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    abstract fun bindMyViewModel(myViewModel: CharactersViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactoryProvider): ViewModelProvider.Factory
}
