package com.cryptopia.android.repository

import android.arch.lifecycle.LiveData
import com.cryptopia.android.model.local.SuggestionPricePair
import com.cryptopia.android.model.remote.CryptoCompareCoinDetail
import com.cryptopia.android.model.remote.CryptoComparePriceHistoricalResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.Query

/**
 * Created by robertzzy on 29/11/17.
 */
/**
 * Created by robertzzy on 18/11/17.
 */

interface PriceRepository {
    fun getAllCachedPricePairs(): LiveData<List<SuggestionPricePair>>

    fun getAndUpdatePricePairs(from: List<String>, to: List<String>, market: String?): LiveData<List<SuggestionPricePair>>
    fun getPricePairs(from: List<String>): LiveData<List<SuggestionPricePair>>

    fun getHistoricalPrice(@Query("fsyms") from: String,
                           @Query("tsyms") to: String,
                           @Query("ts") timeStamp: Long?,
                           @Query("markets") markets: String?): Flowable<CryptoComparePriceHistoricalResponse>

    fun updateCache(from: List<String>, to: List<String>, market: String?): Flowable<List<SuggestionPricePair>>
    fun getTopPricePairs(): LiveData<List<SuggestionPricePair>>
}

interface CoinRepository {
    fun getAllCoinList(): Flowable<List<CryptoCompareCoinDetail>>

    fun getDefaultCoinList(): Flowable<List<CryptoCompareCoinDetail>>

    fun getTopPairs(from: String, limit: Int?): LiveData<List<SuggestionPricePair>>

    fun refreshTopCoinPairs(from: String, limit: Int?): Single<Boolean>

}