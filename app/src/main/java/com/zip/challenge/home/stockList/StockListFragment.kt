package com.zip.challenge.home.stockList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zip.challenge.R
import com.zip.challenge.companyProfile.CompanyProfileActivity
import com.zip.challenge.core.extensions.doOnScroll
import com.zip.challenge.databinding.FragmentStockListBinding
import com.zip.challenge.home.common.MainNavigationFragment
import com.zip.challenge.home.common.OnItemClickCallback
import com.zip.challenge.home.common.StockListAdapter
import com.zip.shared.util.Constants
import com.zip.shared.util.extensions.doOnChange
import com.zip.shared.util.extensions.viewModelProvider
import kotlinx.android.synthetic.main.fragment_stock_list.*

/**
 * [StockListFragment] displays a list of all stocks
 */
class StockListFragment : MainNavigationFragment(), OnItemClickCallback {

    private lateinit var viewModel: StockListViewModel
    private lateinit var binding: FragmentStockListBinding

    private var stockListAdapter = StockListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentStockListBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@StockListFragment.viewModel
            }

        observeViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        viewModel.loadStockList()
    }

    override fun initializeViews() {
        stockListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stockListAdapter

            //Get the first and last visible item on scroll to fetch their company profiles
            doOnScroll { isScrollingDown ->
                if (isScrollingDown) {
                    val firstVisibleItemIndex = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    val lastVisibleItemIndex = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    viewModel.fetchCompanyProfiles(stockListAdapter.getSymbolsAsList(firstVisibleItemIndex, lastVisibleItemIndex))
                }
            }
        }
    }

    override fun observeViewModel() {
        //Update the list with new data
        viewModel.stockListData.doOnChange(this) {
            stockListAdapter.updateList(it)
        }

        viewModel.stockListSuccess.doOnChange(this) {
            if (it) showToast(getString(R.string.stocks_list_success))
        }

        viewModel.toastError.doOnChange(this) {
            showToast(it)
        }

        viewModel.favouriteStock.doOnChange(this) {
            it?.let {
                showToast(
                    getString(if (it.isFavourite) R.string.added_to_favourite else R.string.removed_to_favourite).format(it.symbol)
                )
                viewModel.clearFavouriteStock()
            }
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