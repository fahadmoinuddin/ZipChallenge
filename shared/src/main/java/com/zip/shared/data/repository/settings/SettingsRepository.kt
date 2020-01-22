package com.zip.shared.data.repository.settings

import com.zip.shared.data.prefs.SharedPreferenceStorage
import javax.inject.Inject

/**
 * [SettingsRepository] is to manage preference for dark mode option
 */
class SettingsRepository @Inject constructor(private val preferenceStorage: SharedPreferenceStorage) {

    fun isDarkModeEnabled(): Boolean {
        return preferenceStorage.isDarkMode
    }

    fun setThemeMode(isDarkMode: Boolean) {
        preferenceStorage.isDarkMode = isDarkMode
    }
}