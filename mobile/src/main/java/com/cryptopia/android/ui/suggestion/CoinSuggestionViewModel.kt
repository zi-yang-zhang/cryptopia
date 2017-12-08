package com.cryptopia.android.ui.suggestion

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.cryptopia.android.model.local.TopCoinPair
import com.cryptopia.android.repository.CoinRepository
import javax.inject.Inject

/**
 * Created by robertzzy on 07/12/17.
 */
class CoinSuggestionViewModel @Inject constructor(private var coinRepository: CoinRepository) : ViewModel() {

    fun getTopCoinPairs(from: String, to: String?, limit: Int?): LiveData<List<TopCoinPair>> = coinRepository.getTopPairs(from, to, limit)

}