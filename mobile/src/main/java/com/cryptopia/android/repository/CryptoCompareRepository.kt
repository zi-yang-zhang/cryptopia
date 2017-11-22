package com.cryptopia.android.repository

import com.cryptopia.android.model.local.PricePair
import com.cryptopia.android.model.local.PricePairs
import com.cryptopia.android.model.remote.*
import com.cryptopia.android.network.CryptoCompareAPI
import io.reactivex.Observable
import retrofit2.http.Query
import javax.inject.Inject

/**
 * Created by robertzzy on 18/11/17.
 */

interface PriceRepository {
    fun getPricePairs(from: List<String>, to: List<String>, market: String?): Observable<List<PricePairs>>
    fun getTopPairs(from: String, to: String): Observable<List<CryptoCompareTopCoinPair>>
    fun getHistoricalPrice(@Query("fsyms") from: String,
                           @Query("tsyms") to: String,
                           @Query("ts") timeStamp: Long?,
                           @Query("markets") markets: String?): Observable<CryptoComparePriceHistoricalResponse>
}

class PriceRepositoryImpl @Inject constructor(private val cryptoCompareAPI: CryptoCompareAPI) : PriceRepository {

    override fun getPricePairs(from: List<String>, to: List<String>, market: String?): Observable<List<PricePairs>> =
            cryptoCompareAPI.getPriceFull(from.joinToString(separator = ","), to.joinToString(separator = ","), market)
                    .map(this::convertToPricePairs)

    override fun getTopPairs(from: String, to: String): Observable<List<CryptoCompareTopCoinPair>> =
            cryptoCompareAPI.getTopPairs(from, to, null).map { it.data }

    override fun getHistoricalPrice(@Query("fsyms") from: String,
                                    @Query("tsyms") to: String,
                                    @Query("ts") timeStamp: Long?,
                                    @Query("markets") markets: String?): Observable<CryptoComparePriceHistoricalResponse> =
            cryptoCompareAPI.getHistoricalPrice(from, to, timeStamp, markets)

    private fun convertToPricePairs(from: String,
                                    rawData: Map<String, CryptoComparePriceResponseRawPriceDetail>,
                                    displayData: Map<String, CryptoComparePriceResponseDisplayPriceDetail>): PricePairs {

        val pairs = ArrayList<PricePair>()
        var fromSymbol = ""

        rawData.forEach {
            val display: CryptoComparePriceResponseDisplayPriceDetail? = displayData[it.key]
            if (fromSymbol.isEmpty()) fromSymbol = display?.from ?: ""
            pairs.add(
                    PricePair(it.value.to,
                            it.value.price,
                            display?.price ?: "",
                            display?.to ?: "",
                            it.value.market))
        }
        return PricePairs(from, fromSymbol, pairs)
    }

    private fun convertToPricePairs(response: CryptoComparePriceResponse): List<PricePairs>
            = response.rawData.map { convertToPricePairs(it.key, response.rawData[it.key] ?: mapOf(), response.displayData[it.key] ?: mapOf()) }

}


class CoinRepository @Inject constructor(private val cryptoCompareAPI: CryptoCompareAPI) {
    fun getAllCoinList(): Observable<List<CryptoCompareCoinDetail>> =
            cryptoCompareAPI.getFullCoinList().map { it.data.values.toList() }
    fun getDefaultCoinList(): Observable<List<CryptoCompareCoinDetail>> =
            cryptoCompareAPI.getFullCoinList().map { response ->
                val defaults = response.defaultList.coinIds.split(",")
                response.data.filter { defaults.contains(it.value.id) }.values.toList()
            }
}