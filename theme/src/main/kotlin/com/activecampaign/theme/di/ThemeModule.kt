package com.activecampaign.theme.di

import com.activecampaign.theme.ThemePreferences
import com.activecampaign.theme.data.ThemePreferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ThemeModule {

    @Binds
    @Singleton
    abstract fun bindThemePreferences(
        themePreferencesDataStore: ThemePreferencesDataStore
    ): ThemePreferences
}
