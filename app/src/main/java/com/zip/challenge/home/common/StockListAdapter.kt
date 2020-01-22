package com.zip.challenge.home.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zip.challenge.R
import com.zip.challenge.core.ui.CompanyUIHelper
import com.zip.shared.data.database.stockList.StockListEntity
import com.zip.shared.frameworks.image.ImageLoader
import com.zip.shared.util.extensions.dollarString
import com.zip.shared.util.extensions.emptyIfNull
import kotlinx.android.synthetic.main.list_item_stock.view.*

//listener for add to favourite and item click
interface OnItemClickCallback {
    fun onItemClick(symbol: String)
    fun onFavouriteClicked(symbol: String)
}

/**
 * [StockListAdapter] populates the stocks list from the rool database
 */
class StockListAdapter(private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<StockListAdapter.StockListViewHolder>() {

    private val stockList: ArrayList<StockListEntity> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockListViewHolder {
        return StockListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_stock, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StockListViewHolder, position: Int) {
        holder.bind(stockList[position], onItemClickCallback)
    }

    override fun getItemCount(): Int = stockList.size

    fun updateList(list: List<StockListEntity>) {
        this.stockList.clear()
        this.stockList.addAll(list)
        notifyDataSetChanged()
    }

    //Get symbols with the index range to fetch their company profiles
    fun getSymbolsAsList(fromIndex: Int, toIndex: Int): List<String> {
        return if (fromIndex >= 0) {
            val modifiedToIndex = if (toIndex <= stockList.size) toIndex else stockList.size
            stockList.subList(fromIndex, modifiedToIndex).map { it.symbol }
        } else {
            emptyList()
        }
    }

    class StockListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // bind the data with the list view item
        fun bind(model: StockListEntity, onItemClickCallback: OnItemClickCallback) {
            itemView.stockItemSymbolTextView.text = model.symbol
            itemView.stockItemNameTextView.text = model.name.emptyIfNull()
            itemView.stockItemPriceTextView.text = model.price.dollarString()

            CompanyUIHelper.showChangePercent(itemView.stockItemChangeTextView, model.changePercent)

            itemView.favouriteImageView.setImageResource(
                if (model.isFavourite) R.drawable.ic_favorite_24dp
                else R.drawable.ic_favorite_border_24dp
            )
            itemView.favouriteImageView.setOnClickListener {
                onItemClickCallback.onFavouriteClicked(model.symbol)
            }

            ImageLoader.loadImage(itemView.stockItemImageView, model.image ?: "")

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(model.symbol)
            }
        }
    }
}