package com.example.presentation.navigation

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {
    @Provides
    @Singleton
    fun provideNavigator(
    ): Navigator = NavControllerNavigator()
}