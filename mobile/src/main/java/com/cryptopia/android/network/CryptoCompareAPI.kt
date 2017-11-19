package com.cryptopia.android.network

import com.cryptopia.android.model.remote.CryptoComparePriceResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ziyang.zhang on 2017-11-18.
 */


interface CryptoCompareAPI {
    @GET("data/pricemultifull")
    fun getPriceFull(@Query("fsyms") from: String, @Query("tsyms") to: List<String>): Observable<CryptoComparePriceResponse>


}