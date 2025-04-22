package com.activecampaign.theme

import kotlinx.coroutines.flow.Flow

interface ThemePreferences {

    fun observeIsDarkMode(): Flow<Boolean>

    suspend fun saveDarkMode(isDark: Boolean)
}
