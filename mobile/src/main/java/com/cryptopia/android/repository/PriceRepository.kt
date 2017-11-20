package com.cryptopia.android.repository

import com.cryptopia.android.model.local.PricePair
import com.cryptopia.android.model.local.PricePairs
import com.cryptopia.android.model.remote.CryptoComparePriceResponse
import com.cryptopia.android.network.CryptoCompareAPI
import io.reactivex.Observable

/**
 * Created by robertzzy on 18/11/17.
 */


class PriceRepository {
    lateinit var cryptoCompareAPI: CryptoCompareAPI

    fun getPricePairs(from: String, to: List<String>): Observable<PricePairs> =
            cryptoCompareAPI.getPriceFull(from, to)
                    .map { response -> convertToPricePairs(from, response) }

    private fun convertToPricePairs(from: String, response: CryptoComparePriceResponse): PricePairs {
        val pricePairs = PricePairs(from = from)
        response.rawData[from]?.forEach { pair ->
            pricePairs.pairs.add(
                    PricePair(to = pair.value.to, price = pair.value.price, market = pair.value.market))
        }
        response.displayData[from]?.forEach { pair ->
            if (pricePairs.fromSymbol.isNotEmpty()) pricePairs.fromSymbol = pair.value.from
            val pricePair: PricePair? = pricePairs.pairs.find { pricePair -> pricePair.to == pair.key }
            pricePair?.toSymbol = pair.value.to
            pricePair?.displayPrice = pair.value.price

        }
        return pricePairs
    }
}
