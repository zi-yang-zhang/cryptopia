package com.cryptopia.android.ui.suggestion

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cryptopia.android.R
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.layout_suggestion_coin.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by robertzzy on 07/12/17.
 */
class CoinSuggestionFragment : DaggerFragment() {


    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.layout_suggestion_coin, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vm = ViewModelProviders.of(this, viewModelFactory).get(CoinSuggestionViewModel::class.java)

        coin_list_container.setOnRefreshListener {
            vm.refreshTopCoinPairs("BTC", 5).observeOn(AndroidSchedulers.mainThread()).subscribe({ success ->
                run {
                    Timber.d("Refresh succeed: %s", success)
                    coin_list_container.isRefreshing = false
                }
            })
        }
        coin_list.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
        coin_list.adapter = TopCoinPairAdapter()
        vm.getTopCoinPairs("BTC").observe(this, Observer {
            Timber.d("Top coin pairs updated, refreshing UI, %s", it)

            (coin_list.adapter as TopCoinPairAdapter).updateCoinPairs(it?.toMutableList() ?: mutableListOf())
        })
    }


}