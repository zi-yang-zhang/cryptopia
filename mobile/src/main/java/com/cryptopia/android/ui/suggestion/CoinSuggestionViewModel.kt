package com.cryptopia.android.ui.suggestion

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.cryptopia.android.model.local.PricePair
import com.cryptopia.android.repository.CoinRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by robertzzy on 07/12/17.
 */
class CoinSuggestionViewModel @Inject constructor(private var coinRepository: CoinRepository) : ViewModel() {

    fun getTopCoinPairs(from: String): LiveData<List<PricePair>> = coinRepository.getTopPairs(from, null)

    fun refreshTopCoinPairs(from: String, limit: Int): Single<Boolean> = coinRepository.refreshTopCoinPairs(from, limit)

}