package com.example.rickandmorty.data.di.component

import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.data.di.module.CharactersProvider
import com.example.rickandmorty.data.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CharactersProvider::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    fun viewModelFactory(): ViewModelProvider.Factory // Expose ViewModelFactory
}
