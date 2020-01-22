package com.zip.challenge.home

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.zip.challenge.R
import com.zip.challenge.core.common.BaseActivity
import com.zip.challenge.home.common.NavigationHost
import kotlinx.android.synthetic.main.activity_home.*

/**
 * [HomeActivity] is the main activity which controls the navigation. It has a bottom nav with 3 tabs -
 * Stocks, Favourites, Settings.
 */
class HomeActivity : BaseActivity(), NavigationHost {

    companion object {
        private const val NAV_ID_NONE = -1

        const val EXTRA_NAVIGATION_ID = "extra.NAVIGATION_ID"

        private val TOP_LEVEL_DESTINATIONS = setOf(
            R.id.navigation_stocks_list,
            R.id.navigation_favourites,
            R.id.navigation_settings
        )
    }

    private lateinit var navController: NavController
    private var navHostFragment: NavHostFragment? = null
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var currentNavId = NAV_ID_NONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Initialising Navigation controller and connecting with bottom navigation
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.homeNavHostFragment) as? NavHostFragment ?: return

        navController = findNavController(R.id.homeNavHostFragment)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            currentNavId = destination.id
        }
        appBarConfiguration = AppBarConfiguration(TOP_LEVEL_DESTINATIONS)
        homeBottomNavView.apply {
            setupWithNavController(navController)
        }

        if (savedInstanceState == null) {
            val initialNavId = intent?.getIntExtra(EXTRA_NAVIGATION_ID, R.id.navigation_stocks_list) ?: R.id.navigation_stocks_list
            homeBottomNavView.selectedItemId = initialNavId
            navigateTo(initialNavId)
        }
    }

    //Navigate the selected tab. Handled automatically by the Nav Controller
    private fun navigateTo(navId: Int) {
        if (navId == currentNavId) return
        navController.navigate(navId)
    }

    //Callback method to update the toolbar's title based on the selected bottom tab
    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}
