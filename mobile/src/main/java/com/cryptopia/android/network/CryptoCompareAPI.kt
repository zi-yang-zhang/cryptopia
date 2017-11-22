package com.cryptopia.android.network

import com.cryptopia.android.model.remote.CryptoCompareCoinListResponse
import com.cryptopia.android.model.remote.CryptoComparePriceHistoricalResponse
import com.cryptopia.android.model.remote.CryptoComparePriceResponse
import com.cryptopia.android.model.remote.CryptoCompareTopCoinPairsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ziyang.zhang on 2017-11-18.
 */


interface CryptoCompareAPI {
    @GET("data/pricemultifull")
    fun getPriceFull(@Query("fsyms") from: String, @Query("tsyms") to: String, @Query("e") exchange: String?): Observable<CryptoComparePriceResponse>

    @GET("data/all/coinlist")
    fun getFullCoinList(): Observable<CryptoCompareCoinListResponse>

    @GET("data/top/pairs")
    fun getTopPairs(@Query("fsyms") from: String, @Query("tsyms") to: String, @Query("limit") limit: Int?): Observable<CryptoCompareTopCoinPairsResponse>

    @GET("data/pricehistorical")
    fun getHistoricalPrice(@Query("fsyms") from: String, @Query("tsyms") to: String, @Query("ts") timeStamp: Long?, @Query("markets") markets: String?): Observable<CryptoComparePriceHistoricalResponse>
}