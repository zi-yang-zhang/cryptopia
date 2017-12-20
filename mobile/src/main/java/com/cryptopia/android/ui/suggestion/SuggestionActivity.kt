package com.cryptopia.android.ui.suggestion

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.cryptopia.android.R
import com.cryptopia.android.ui.auth.SignInActivity
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_suggestion.*
import kotlinx.android.synthetic.main.part_suggestion.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by robertzzy on 07/12/17.
 */
class SuggestionActivity : DaggerAppCompatActivity(), AppBarLayout.OnOffsetChangedListener, AdapterView.OnItemSelectedListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.suggestion_log_in, R.id.suggestion_sign_up -> {
                toSignIn()
            }
        }

    }

    private fun toSignIn() {
        intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    override fun onRefresh() {
        if (!refreshAction.isDisposed) refreshAction.dispose()
        refreshAction = vm.refreshTopCoinPairs(suggestion_coin_selector.selectedItem.toString(), 5).observeOn(AndroidSchedulers.mainThread()).subscribe({ success ->
            run {
                Timber.d("Refresh succeed: %s", success)
                coin_list_container.isRefreshing = false
            }
        })
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (!refreshAction.isDisposed) refreshAction.dispose()
        refreshAction = vm.refreshTopCoinPairs(parent?.getItemAtPosition(position).toString(), 5).observeOn(AndroidSchedulers.mainThread()).subscribe({ success ->
            run {
                Timber.d("Refresh succeed: %s", success)
                coin_list_container.isRefreshing = false
            }
        })
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        coin_list_container.isEnabled = verticalOffset == 0
    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var vm: CoinSuggestionViewModel
    private lateinit var refreshAction: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_suggestion)
        setSupportActionBar(suggestion_toolbar)


        vm = ViewModelProviders.of(this, viewModelFactory).get(CoinSuggestionViewModel::class.java)
        coin_list_container.setOnRefreshListener(this)
        coin_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        coin_list.adapter = TopCoinPairAdapter()
        vm.getTopCoinPairs().observe(this, Observer {
            Timber.d("Top coin pairs updated, refreshing UI, %s", it)
            (coin_list.adapter as TopCoinPairAdapter).updateCoinPairs(it?.toMutableList() ?: mutableListOf())
        })
        val suggestionAdapter = ArrayAdapter.createFromResource(this, R.array.suggestion_coin, R.layout.suggestion_spinner)
        suggestionAdapter.setDropDownViewResource(R.layout.suggestion_dropdown)
        suggestion_coin_selector.adapter = suggestionAdapter
        suggestion_coin_selector.onItemSelectedListener = this
        suggestion_log_in.setOnClickListener(this)
        suggestion_sign_up.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        refreshAction = vm.refreshTopCoinPairs(suggestion_coin_selector.selectedItem.toString(), 5).subscribe()
        suggestion_toolbar_container.addOnOffsetChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        if (!refreshAction.isDisposed) refreshAction.dispose()
        suggestion_toolbar_container.removeOnOffsetChangedListener(this)
    }
}