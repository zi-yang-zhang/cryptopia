package com.cryptopia.android.repository

import android.arch.lifecycle.LiveData
import com.cryptopia.android.model.local.PricePair
import com.cryptopia.android.model.remote.CryptoCompareCoinDetail
import com.cryptopia.android.model.remote.CryptoComparePriceHistoricalResponse
import com.cryptopia.android.model.remote.CryptoCompareTopCoinPair
import io.reactivex.Flowable
import retrofit2.http.Query

/**
 * Created by robertzzy on 29/11/17.
 */
/**
 * Created by robertzzy on 18/11/17.
 */

interface PriceRepository {
    fun getAllCachedPricePairs(): LiveData<List<PricePair>>

    fun getPricePairs(from: List<String>, to: List<String>, market: String?): LiveData<List<PricePair>>
    fun getPricePairs(from: List<String>): LiveData<List<PricePair>>

    fun getTopPairs(from: String, to: String): Flowable<List<CryptoCompareTopCoinPair>>
    fun getHistoricalPrice(@Query("fsyms") from: String,
                           @Query("tsyms") to: String,
                           @Query("ts") timeStamp: Long?,
                           @Query("markets") markets: String?): Flowable<CryptoComparePriceHistoricalResponse>

    fun updateCache(from: List<String>, to: List<String>, market: String?): Flowable<List<PricePair>>
}

interface CoinRepository {
    fun getAllCoinList(): Flowable<List<CryptoCompareCoinDetail>>

    fun getDefaultCoinList(): Flowable<List<CryptoCompareCoinDetail>>
}