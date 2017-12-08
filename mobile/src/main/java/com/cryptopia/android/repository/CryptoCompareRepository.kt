package com.cryptopia.android.repository

import android.arch.lifecycle.LiveData
import com.cryptopia.android.model.local.CoinPairDao
import com.cryptopia.android.model.local.PricePair
import com.cryptopia.android.model.local.PricePairDAO
import com.cryptopia.android.model.local.TopCoinPair
import com.cryptopia.android.model.remote.*
import com.cryptopia.android.network.CryptoCompareAPI
import io.reactivex.Flowable
import retrofit2.http.Query
import java.util.*
import javax.inject.Inject

class PriceRepositoryImpl @Inject constructor(private val cryptoCompareAPI: CryptoCompareAPI, private val dao: PricePairDAO) : PriceRepository {
    override fun updateCache(from: List<String>, to: List<String>, market: String?): Flowable<List<PricePair>> =
            cryptoCompareAPI.getPriceFull(from.joinToString(separator = ","), to.joinToString(separator = ","), market)
                    .map(this::convertToPricePairs)

    override fun getPricePairs(from: List<String>): LiveData<List<PricePair>> = dao.getPricePairs(from)

    override fun getAllCachedPricePairs(): LiveData<List<PricePair>> = dao.getAllPricePairs()


    override fun getPricePairs(from: List<String>, to: List<String>, market: String?): LiveData<List<PricePair>> {
        updateCache(from, to, market).subscribe { dao.cachePricePairs(it) }
        return if (market == null) dao.getPricePairs(from, to) else dao.getPricePairs(from, to, market)
    }




    override fun getHistoricalPrice(@Query("fsyms") from: String,
                                    @Query("tsyms") to: String,
                                    @Query("ts") timeStamp: Long?,
                                    @Query("markets") markets: String?): Flowable<CryptoComparePriceHistoricalResponse> =
            cryptoCompareAPI.getHistoricalPrice(from, to, timeStamp, markets)

    private fun convertToPricePairs(rawData: Map<String, CryptoComparePriceResponseRawPriceDetail>,
                                    displayData: Map<String, CryptoComparePriceResponseDisplayPriceDetail>): List<PricePair> {

        val pairs = ArrayList<PricePair>()
        var fromSymbol = ""

        rawData.forEach {
            val display: CryptoComparePriceResponseDisplayPriceDetail? = displayData[it.key]
            if (fromSymbol.isEmpty()) fromSymbol = display?.from ?: ""
            pairs.add(
                    PricePair(it.value.from,
                            it.value.to,
                            it.value.price,
                            display?.price ?: "",
                            display?.to ?: "",
                            display?.from ?: "",
                            it.value.market,
                            Date()))
        }
        return pairs
    }

    private fun convertToPricePairs(response: CryptoComparePriceResponse): List<PricePair>
            = response.rawData.flatMap { convertToPricePairs(response.rawData[it.key] ?: mapOf(), response.displayData[it.key] ?: mapOf()) }

}


class CoinRepositoryImpl @Inject constructor(private val cryptoCompareAPI: CryptoCompareAPI, private val dao: CoinPairDao) : CoinRepository {
    override fun getAllCoinList(): Flowable<List<CryptoCompareCoinDetail>> =
            cryptoCompareAPI.getFullCoinList().map { it.data.values.toList() }

    override fun getDefaultCoinList(): Flowable<List<CryptoCompareCoinDetail>> =
            cryptoCompareAPI.getFullCoinList().map { response ->
                val defaults = response.defaultList.coinIds.split(",")
                response.data.filter { defaults.contains(it.value.id) }.values.toList()
            }

    override fun getTopPairs(from: String, to: String?, limit: Int?): LiveData<List<TopCoinPair>> {
        cryptoCompareAPI.getTopPairs(from, to, limit).map { it.data }.subscribe { topCoinPairs ->
            dao.clearTopCoinPairs().subscribe { dao.addTopCoinPairs(topCoinPairs) }
        }
        return dao.getTopCoinPairs()
    }

}