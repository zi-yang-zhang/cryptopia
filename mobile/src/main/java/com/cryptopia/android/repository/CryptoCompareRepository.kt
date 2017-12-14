package com.cryptopia.android.repository

import android.arch.lifecycle.LiveData
import com.cryptopia.android.model.local.CoinPairDao
import com.cryptopia.android.model.local.PricePairDAO
import com.cryptopia.android.model.local.SuggestionPricePair
import com.cryptopia.android.model.remote.*
import com.cryptopia.android.network.CryptoCompareAPI
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.Query
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class PriceRepositoryImpl @Inject constructor(private val cryptoCompareAPI: CryptoCompareAPI, private val dao: PricePairDAO) : PriceRepository {
    override fun updateCache(from: List<String>, to: List<String>, market: String?): Flowable<List<SuggestionPricePair>> =
            cryptoCompareAPI.getPriceFull(from.joinToString(separator = ","), to.joinToString(separator = ","), market)
                    .map(this::convertToPricePairs).doOnNext { if (dao.update(it) == 0) dao.insert(it) }

    override fun getPricePairs(from: List<String>): LiveData<List<SuggestionPricePair>> = dao.getSuggestionPricePairs(from)

    override fun getAllCachedPricePairs(): LiveData<List<SuggestionPricePair>> = dao.getAllSuggestionPricePairs()


    override fun getAndUpdatePricePairs(from: List<String>, to: List<String>, market: String?): LiveData<List<SuggestionPricePair>> {
        updateCache(from, to, market).subscribe()
        return if (market == null) dao.getSuggestionPricePairs(from, to) else dao.getSuggestionPricePairs(from, to, market)
    }


    override fun getHistoricalPrice(@Query("fsyms") from: String,
                                    @Query("tsyms") to: String,
                                    @Query("ts") timeStamp: Long?,
                                    @Query("markets") markets: String?): Flowable<CryptoComparePriceHistoricalResponse> =
            cryptoCompareAPI.getHistoricalPrice(from, to, timeStamp, markets)

    override fun getTopPricePairs(): LiveData<List<SuggestionPricePair>> = dao.getTopPricePairs()

    private fun convertToPricePairs(rawData: Map<String, CryptoComparePriceResponseRawPriceDetail>,
                                    displayData: Map<String, CryptoComparePriceResponseDisplayPriceDetail>): List<SuggestionPricePair> {

        val pairs = ArrayList<SuggestionPricePair>()
        var fromSymbol = ""

        rawData.forEach {
            val display: CryptoComparePriceResponseDisplayPriceDetail? = displayData[it.key]
            if (fromSymbol.isEmpty()) fromSymbol = display?.from ?: ""
            pairs.add(
                    SuggestionPricePair(fromCoin = it.value.from,
                            toCoin = it.value.to,
                            price = it.value.price,
                            displayPrice = display?.price ?: "",
                            fromCoinDisplay = display?.from ?: "",
                            toCoinDisplay = display?.to ?: "",
                            indexMarket = it.value.market,
                            updatedAt = it.value.lastUpdated,
                            exchangeSymbol = display?.exchange ?: "",
                            exchange = it.value.exchange,
                            changePercentageOfDay = it.value.changePercentageOfDay,
                            changeOfDay = it.value.changeOfDay))
        }
        return pairs
    }

    private fun convertToPricePairs(response: CryptoComparePriceResponse): List<SuggestionPricePair>
            = response.rawData.flatMap { convertToPricePairs(response.rawData[it.key] ?: mapOf(), response.displayData[it.key] ?: mapOf()) }

}


class CoinRepositoryImpl @Inject constructor(private val cryptoCompareAPI: CryptoCompareAPI, private val coinPairDao: CoinPairDao, private val priceRepository: PriceRepository) : CoinRepository {
    override fun refreshTopCoinPairs(from: String, limit: Int?): Single<Boolean> {
        return cryptoCompareAPI.getTopPairs(from, limit).map { it.data }.doOnNext { topCoinPairs ->
            Timber.d("Top coin pairs updated, refreshing cache")
            coinPairDao.clearTopCoinPairs()
            if (coinPairDao.update(topCoinPairs) == 0) coinPairDao.insert(topCoinPairs)
        }.flatMap {
            Flowable.merge(it.map {
                Timber.d("Get price data: %s", it)
                priceRepository.updateCache(listOf(it.fromCoin), listOf(it.toCoin), null)
            }, 1)
        }.reduce { aggregated: List<SuggestionPricePair>, newItems: List<SuggestionPricePair> -> aggregated.plus(newItems) }.map { it.isNotEmpty() }.onErrorReturn { true }.toSingle()
    }

    override fun getAllCoinList(): Flowable<List<CryptoCompareCoinDetail>> =
            cryptoCompareAPI.getFullCoinList().map { it.data.values.toList() }

    override fun getDefaultCoinList(): Flowable<List<CryptoCompareCoinDetail>> =
            cryptoCompareAPI.getFullCoinList().map { response ->
                val defaults = response.defaultList.coinIds.split(",")
                response.data.filter { defaults.contains(it.value.id) }.values.toList()
            }

    override fun getTopPairs(from: String, limit: Int?): LiveData<List<SuggestionPricePair>> {
        refreshTopCoinPairs(from, limit).subscribe()
        return priceRepository.getTopPricePairs()
    }

}