package com.zip.challenge.ui

import android.content.Intent
import androidx.test.rule.ActivityTestRule
import com.zip.challenge.home.HomeActivity

class HomeActivityTestRule(private val initialNavId: Int) : ActivityTestRule<HomeActivity>(HomeActivity::class.java) {

    override fun getActivityIntent():Intent {
        return Intent(Intent.ACTION_MAIN).apply {
            putExtra(HomeActivity.EXTRA_NAVIGATION_ID, initialNavId)
        }
    }
}