package com.cryptopia.android.ui.suggestion

import android.support.v4.content.res.ResourcesCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cryptopia.android.R
import com.cryptopia.android.model.local.SuggestionPricePair
import kotlinx.android.synthetic.main.adapter_top_coin_pair.view.*

/**
 * Created by robertzzy on 08/12/17.
 */
class TopCoinPairAdapter : RecyclerView.Adapter<TopCoinPairView>() {

    private var topCoinPairSuggestions: MutableList<SuggestionPricePair> = mutableListOf()
    override fun onBindViewHolder(holder: TopCoinPairView?, position: Int) {
        holder?.bindValue(topCoinPairSuggestions[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TopCoinPairView =
            TopCoinPairView(LayoutInflater.from(parent?.context).inflate(R.layout.adapter_top_coin_pair, parent, false))


    override fun getItemCount(): Int = topCoinPairSuggestions.size

    fun updateCoinPairs(coinPairSuggestions: MutableList<SuggestionPricePair>) {
        coinPairSuggestions.sortByDescending { it.changePercentageOfDay }

        if (topCoinPairSuggestions.size == 0) {
            topCoinPairSuggestions.addAll(coinPairSuggestions)
            notifyItemRangeInserted(0, coinPairSuggestions.size)
        } else {
            val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(TopCoinPairDiff(coinPairSuggestions, topCoinPairSuggestions), true)
            topCoinPairSuggestions = coinPairSuggestions
            result.dispatchUpdatesTo(this)
        }
    }
}


class TopCoinPairView(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindValue(suggestionPricePair: SuggestionPricePair) {
        itemView.top_coin_pair_exchange.text = suggestionPricePair.exchangeSymbol
        itemView.top_coin_pair_from.text = suggestionPricePair.fromCoin
        itemView.top_coin_pair_to.text = suggestionPricePair.toCoin
        itemView.top_coin_pair_price.text = suggestionPricePair.displayPrice
        if (suggestionPricePair.changePercentageOfDay > 0) itemView.top_coin_pair_change_percentage.setTextColor(ResourcesCompat.getColor(itemView.resources, R.color.colorIncrease, null))
        else itemView.top_coin_pair_change_percentage.setTextColor(ResourcesCompat.getColor(itemView.resources, R.color.colorDecrease, null))
        itemView.top_coin_pair_change_percentage.text = String.format("%.3f(%.2f)", suggestionPricePair.changePercentageOfDay, suggestionPricePair.changeOfDay)
    }
}

class TopCoinPairDiff(private val newList: List<SuggestionPricePair>, private val oldList: List<SuggestionPricePair>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newSuggestionPricePair = newList[newItemPosition]
        val oldSuggestionPricePair = oldList[oldItemPosition]
        return newSuggestionPricePair.fromCoin == oldSuggestionPricePair.fromCoin && newSuggestionPricePair.toCoin == oldSuggestionPricePair.toCoin
    }

    override fun getOldListSize(): Int = oldList.size


    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = newList[newItemPosition] == oldList[oldItemPosition]


}