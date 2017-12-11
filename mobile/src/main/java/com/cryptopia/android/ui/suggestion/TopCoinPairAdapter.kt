package com.cryptopia.android.ui.suggestion

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cryptopia.android.R
import com.cryptopia.android.model.local.PricePair
import kotlinx.android.synthetic.main.adapter_top_coin_pair.view.*

/**
 * Created by robertzzy on 08/12/17.
 */
class TopCoinPairAdapter : RecyclerView.Adapter<TopCoinPairView>() {

    var topCoinPairs: MutableList<PricePair> = mutableListOf()
    override fun onBindViewHolder(holder: TopCoinPairView?, position: Int) {
        holder?.bindValue(topCoinPairs[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TopCoinPairView =
            TopCoinPairView(LayoutInflater.from(parent?.context).inflate(R.layout.adapter_top_coin_pair, parent, false))


    override fun getItemCount(): Int = topCoinPairs.size

    fun updateCoinPairs(coinPairs: MutableList<PricePair>) {
        topCoinPairs = coinPairs
        notifyDataSetChanged()
    }
}


class TopCoinPairView(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindValue(pricePair: PricePair) {
        itemView.top_coin_pair_exchange.text = pricePair.exchange
        itemView.top_coin_pair_from.text = pricePair.fromCoinDisplay
        itemView.top_coin_pair_to.text = pricePair.toCoinDisplay
        itemView.top_coin_pair_price.text = pricePair.displayPrice
        itemView.top_coin_pair_change_percentage.text = "%.2f".format(pricePair.changePercentageOfDay)
    }
}