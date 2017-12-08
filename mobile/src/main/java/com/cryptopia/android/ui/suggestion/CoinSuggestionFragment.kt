package com.cryptopia.android.ui.suggestion

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.layout_suggestion_coin.*
import javax.inject.Inject

/**
 * Created by robertzzy on 07/12/17.
 */
class CoinSuggestionFragment : DaggerFragment() {


    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coin_list.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
        val vm = ViewModelProviders.of(this, viewModelFactory).get(CoinSuggestionViewModel::class.java)
        vm.getTopCoinPairs("BTC", null, null).observe(this, Observer { (coin_list.adapter as TopCoinPairAdapter).topCoinPairs = it?.toMutableList() ?: mutableListOf() })
    }
}