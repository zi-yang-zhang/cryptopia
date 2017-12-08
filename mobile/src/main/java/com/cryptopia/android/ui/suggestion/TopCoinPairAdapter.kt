package com.cryptopia.android.ui.suggestion

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.cryptopia.android.model.local.TopCoinPair

/**
 * Created by robertzzy on 08/12/17.
 */
class TopCoinPairAdapter : RecyclerView.Adapter<TopCoinPairView>() {

    var topCoinPairs: MutableList<TopCoinPair> = mutableListOf()
    override fun onBindViewHolder(holder: TopCoinPairView?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TopCoinPairView {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


class TopCoinPairView(itemView: View) : RecyclerView.ViewHolder(itemView)