package com.example.rickandmorty.data.di.component

import androidx.lifecycle.ViewModelProvider
import com.example.common.module.ApolloModule
import com.example.data.di.CharacterModule
import com.example.data.di.DispatchersModule
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.data.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CharacterModule::class, ViewModelModule::class, ApolloModule::class, DispatchersModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    fun viewModelFactory(): ViewModelProvider.Factory
}
