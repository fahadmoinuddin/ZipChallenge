package com.zip.challenge.ui

import androidx.test.core.app.ApplicationProvider
import com.zip.shared.data.prefs.SharedPreferenceStorage
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Rules to set default values for shared preferences
 */
class SetDisableDarkModePreferenceRule : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        SharedPreferenceStorage(ApplicationProvider.getApplicationContext()).apply {
            isDarkMode = false
        }
    }
}