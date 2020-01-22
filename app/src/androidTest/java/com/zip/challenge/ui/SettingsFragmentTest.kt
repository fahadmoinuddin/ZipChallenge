package com.zip.challenge.ui

import android.os.SystemClock
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zip.challenge.R
import com.zip.shared.data.prefs.SharedPreferenceStorage
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Espresso test for Stocks list screen
 */
@RunWith(AndroidJUnit4::class)
class SettingsFragmentTest {

    @get:Rule
    var activityRule = HomeActivityTestRule(R.id.navigation_settings)

    @get:Rule
    var preferenceRule = SetDisableDarkModePreferenceRule()

    @Test
    fun stockList_basicViewsDisplayed() {
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar))))
            .check(matches(withText("Settings")))

        onView(withId(R.id.settingsDarkModeSwitch))
            .perform(click())

        SystemClock.sleep(1000)

        val preference = SharedPreferenceStorage(ApplicationProvider.getApplicationContext())
        assert(preference.isDarkMode)
    }
}