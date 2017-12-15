package com.cryptopia.android.ui.suggestion

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.LinearLayoutManager
import com.cryptopia.android.R
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.layout_suggestion.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by robertzzy on 07/12/17.
 */
class SuggestionActivity : DaggerAppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        coin_list_container.isEnabled = verticalOffset == 0
    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_suggestion)
        setSupportActionBar(suggestion_toolbar)


        val vm = ViewModelProviders.of(this, viewModelFactory).get(CoinSuggestionViewModel::class.java)

        coin_list_container.setOnRefreshListener {
            vm.refreshTopCoinPairs("BTC", 5).observeOn(AndroidSchedulers.mainThread()).subscribe({ success ->
                run {
                    Timber.d("Refresh succeed: %s", success)
                    coin_list_container.isRefreshing = false
                }
            })
        }
        coin_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        coin_list.adapter = TopCoinPairAdapter()
        vm.getTopCoinPairs("BTC").observe(this, Observer {
            Timber.d("Top coin pairs updated, refreshing UI, %s", it)
            (coin_list.adapter as TopCoinPairAdapter).updateCoinPairs(it?.toMutableList() ?: mutableListOf())
        })

    }

    override fun onResume() {
        super.onResume()
        suggestion_toolbar_container.addOnOffsetChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        suggestion_toolbar_container.removeOnOffsetChangedListener(this)

    }
}