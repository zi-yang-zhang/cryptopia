package com.cryptopia.android.network

import com.cryptopia.android.model.remote.CryptoCompareCoinListResponse
import com.cryptopia.android.model.remote.CryptoComparePriceHistoricalResponse
import com.cryptopia.android.model.remote.CryptoComparePriceResponse
import com.cryptopia.android.model.remote.CryptoCompareTopCoinPairsResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ziyang.zhang on 2017-11-18.
 */


interface CryptoCompareAPI {
    @GET("data/pricemultifull")
    fun getPriceFull(@Query("fsyms") from: String, @Query("tsyms") to: String, @Query("e") exchange: String?): Flowable<CryptoComparePriceResponse>

    @GET("data/all/coinlist")
    fun getFullCoinList(): Flowable<CryptoCompareCoinListResponse>

    @GET("data/top/pairs")
    fun getTopPairs(@Query("fsym") from: String, @Query("limit") limit: Int?): Flowable<CryptoCompareTopCoinPairsResponse>

    @GET("data/pricehistorical")
    fun getHistoricalPrice(@Query("fsyms") from: String, @Query("tsyms") to: String, @Query("ts") timeStamp: Long?, @Query("markets") markets: String?): Flowable<CryptoComparePriceHistoricalResponse>
}