package com.zip.challenge.home.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zip.challenge.BuildConfig
import com.zip.challenge.core.common.BaseViewModel
import com.zip.shared.data.repository.settings.SettingsRepository
import javax.inject.Inject

/**
 * [SettingsViewModel] acts as the intermediate channel between [SettingsFragment] and
 * [SettingsRepository]
 */
class SettingsViewModel @Inject constructor(private val repository: SettingsRepository) : BaseViewModel() {

    //LiveData to listen for toggle changes which indicates whether user selected dark mode or not
    private val _isDarkMode = MutableLiveData(repository.isDarkModeEnabled())
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    //Version name to display in view
    val version = "ver ${BuildConfig.VERSION_NAME}"

    //Called from xml on Switch toggle
    fun onThemeChanged(isDarkMode: Boolean) {
        //Update the setting in SharedPreference
        repository.setThemeMode(isDarkMode)
        this@SettingsViewModel._isDarkMode.value = isDarkMode
    }
}