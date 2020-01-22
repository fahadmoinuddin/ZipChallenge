package com.zip.challenge.ui

import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zip.challenge.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Espresso test for Stocks list screen
 */
@RunWith(AndroidJUnit4::class)
class FavouritesFragmentTest {

    @get:Rule
    var activityRule = HomeActivityTestRule(R.id.navigation_favourites)

    @Test
    fun stockList_basicViewsDisplayed() {
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar))))
            .check(matches(withText("Favourites")))
    }
}