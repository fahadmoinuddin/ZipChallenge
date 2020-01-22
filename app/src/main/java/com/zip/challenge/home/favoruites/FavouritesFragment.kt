package com.zip.challenge.home.favoruites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.zip.challenge.R
import com.zip.challenge.companyProfile.CompanyProfileActivity
import com.zip.challenge.databinding.FragmentFavouritesBinding
import com.zip.challenge.home.common.MainNavigationFragment
import com.zip.challenge.home.common.OnItemClickCallback
import com.zip.challenge.home.common.StockListAdapter
import com.zip.shared.util.Constants
import com.zip.shared.util.extensions.doOnChange
import com.zip.shared.util.extensions.viewModelProvider
import kotlinx.android.synthetic.main.fragment_favourites.*

/**
 * [FavouritesFragment] displays the list of favourites added by the user.
 * This fragment displays the refreshed price every 15 seconds. User can remove from favourites.
 */
class FavouritesFragment : MainNavigationFragment(), OnItemClickCallback {

    private lateinit var viewModel: FavouritesViewModel
    private lateinit var binding: FragmentFavouritesBinding

    private val favouritesAdapter = StockListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@FavouritesFragment.viewModel
            }

        observeViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()

        /**
         * Go to [com.zip.challenge.home.stockList.StockListFragment] on back press
         */
        val navController = Navigation.findNavController(view)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.popBackStack(R.id.navigation_stocks_list, false)
            }
        })
    }

    override fun initializeViews() {
        favouritesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favouritesAdapter
        }
    }

    override fun observeViewModel() {
        viewModel.favouritesListData.doOnChange(this) {
            if (it.isEmpty()) {
                // show empty view if no favourites
                viewModel.isFavouritesEmpty(true)
            } else {
                favouritesAdapter.updateList(it)
            }
        }

        //show toast on error
        viewModel.toastError.doOnChange(this) {
            showToast(it)
        }

        //show add/remove favourite status as toast
        viewModel.favouriteStock.doOnChange(this) {
            showToast(
                getString(if (it.isFavourite) R.string.added_to_favourite else R.string.removed_to_favourite).format(it.symbol)
            )
        }
    }

    //Callback for favourite click
    override fun onFavouriteClicked(symbol: String) {
        viewModel.updateFavouriteStatus(symbol)
    }

    //Callback for item click
    override fun onItemClick(symbol: String) {
        requireActivity().run {
            startActivity(
                Intent(this, CompanyProfileActivity::class.java)
                    .apply { putExtra(Constants.EXTRA_SYMBOL, symbol) }
            )
        }
    }
}