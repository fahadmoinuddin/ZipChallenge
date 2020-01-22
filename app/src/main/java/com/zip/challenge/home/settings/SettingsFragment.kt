package com.zip.challenge.home.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.zip.challenge.R
import com.zip.challenge.databinding.FragmentSettingsBinding
import com.zip.challenge.home.common.MainNavigationFragment
import com.zip.shared.util.ThemeHelper
import com.zip.shared.util.ThemeMode
import com.zip.shared.util.extensions.doOnChange
import com.zip.shared.util.extensions.viewModelProvider

/***
 * [SettingsFragment] shows a setting to enable/disable dark mode for the app.
 * The setting is stored in SharedPrefernces.
 */
class SettingsFragment : MainNavigationFragment() {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@SettingsFragment.viewModel
            }
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Go to StockListActivity on back press
        val navController = Navigation.findNavController(view)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.popBackStack(R.id.navigation_stocks_list, false)
            }
        })
    }

    override fun initializeViews() {}

    override fun observeViewModel() {
        //Change theme
        viewModel.isDarkMode.doOnChange (this) {
            ThemeHelper.applyTheme(if (it) ThemeMode.Dark else ThemeMode.Light)
        }
    }
}