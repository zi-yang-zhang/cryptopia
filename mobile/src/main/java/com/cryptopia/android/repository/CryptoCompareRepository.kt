package com.cryptopia.android.repository

import com.cryptopia.android.model.local.PricePair
import com.cryptopia.android.model.local.PricePairs
import com.cryptopia.android.model.remote.CryptoCompareCoinDetail
import com.cryptopia.android.model.remote.CryptoComparePriceResponse
import com.cryptopia.android.model.remote.CryptoComparePriceResponseDisplayPriceDetail
import com.cryptopia.android.model.remote.CryptoComparePriceResponseRawPriceDetail
import com.cryptopia.android.network.CryptoCompareAPI
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by robertzzy on 18/11/17.
 */


class PriceRepository @Inject constructor(private val cryptoCompareAPI: CryptoCompareAPI) {

    fun getPricePairs(from: List<String>, to: List<String>): Observable<List<PricePairs>> =
            cryptoCompareAPI.getPriceFull(from.joinToString(separator = ","), to.joinToString(separator = ","))
                    .map { response -> convertToPricePairs(from, response) }

    private fun convertToPricePairs(from: String, rawData: Map<String, CryptoComparePriceResponseRawPriceDetail>, displayData: Map<String, CryptoComparePriceResponseDisplayPriceDetail>): PricePairs {

        val pairs = ArrayList<PricePair>()
        var fromSymbol = ""

        rawData.forEach { raw ->
            val display: CryptoComparePriceResponseDisplayPriceDetail? = displayData[raw.key]
            if (fromSymbol.isEmpty()) fromSymbol = display?.from ?: ""
            pairs.add(
                    PricePair(raw.value.to, raw.value.price, display?.price ?: "", display?.to ?: "", raw.value.market))
        }
        return PricePairs(from, fromSymbol, pairs)
    }

    private fun convertToPricePairs(queries: List<String>, response: CryptoComparePriceResponse): List<PricePairs>
            = queries.map { from -> convertToPricePairs(from, response.rawData[from] ?: mapOf(), response.displayData[from] ?: mapOf()) }

}


class CoinRepository @Inject constructor(private val cryptoCompareAPI: CryptoCompareAPI) {
    fun getAllCoinList(): Observable<List<CryptoCompareCoinDetail>> = cryptoCompareAPI.getFullCoinList().map { response -> response.data.values.toList() }
    fun getDefaultCoinList(): Observable<List<CryptoCompareCoinDetail>> =
            cryptoCompareAPI.getFullCoinList().map { response ->
                val defaults = response.defaultList.coinIds.split(",")
                response.data.filter { coinData -> defaults.contains(coinData.value.id) }.values.toList()
            }
}