package com.cryptopia.android.model.remote

import com.cryptopia.android.model.local.TopCoinPair
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by ziyang.zhang on 2017-11-18.
 */


data class CryptoComparePriceResponse(@SerializedName("RAW")
                                      @Expose
                                      val rawData: Map<String, Map<String, CryptoComparePriceResponseRawPriceDetail>> = mapOf(),
                                      @SerializedName("DISPLAY")
                                      @Expose
                                      val displayData: Map<String, Map<String, CryptoComparePriceResponseDisplayPriceDetail>> = mapOf())

data class CryptoComparePriceResponseRawPriceDetail(@SerializedName("MARKET")
                                                    @Expose
                                                    val market: String = "",
                                                    @SerializedName("FROMSYMBOL")
                                                    @Expose
                                                    val from: String = "",
                                                    @SerializedName("TOSYMBOL")
                                                    @Expose
                                                    val to: String = "",
                                                    @SerializedName("PRICE")
                                                    @Expose
                                                    val price: Double = 0.0,
                                                    @SerializedName("LASTMARKET")
                                                    @Expose
                                                    val exchange: String = "",
                                                    @SerializedName("CHANGEDAY")
                                                    @Expose
                                                    val changeOfDay: Double = 0.0,
                                                    @SerializedName("CHANGEPCTDAY")
                                                    @Expose
                                                    val changePercentageOfDay: Double = 0.0,
                                                    @SerializedName("LASTUPDATE")
                                                    @Expose
                                                    val lastUpdated: Long = 0)

data class CryptoComparePriceResponseDisplayPriceDetail(@SerializedName("MARKET")
                                                        @Expose
                                                        val market: String = "",
                                                        @SerializedName("FROMSYMBOL")
                                                        @Expose
                                                        val from: String = "",
                                                        @SerializedName("TOSYMBOL")
                                                        @Expose
                                                        val to: String = "",
                                                        @SerializedName("PRICE")
                                                        @Expose
                                                        val price: String = "",
                                                        @SerializedName("LASTMARKET")
                                                        @Expose
                                                        val exchange: String = "",
                                                        @SerializedName("CHANGEDAY")
                                                        @Expose
                                                        val changeOfDay: String = "",
                                                        @SerializedName("CHANGEPCTDAY")
                                                        @Expose
                                                        val changePercentageOfDay: String = "")

data class CryptoCompareCoinListResponse(@SerializedName("DefaultWatchlist")
                                         @Expose
                                         val defaultList: CryptoCompareCoinListDefaultList = CryptoCompareCoinListDefaultList(),
                                         @SerializedName("Data")
                                         @Expose
                                         val data: Map<String, CryptoCompareCoinDetail>)

data class CryptoCompareCoinListDefaultList(@SerializedName("CoinIs") @Expose val coinIds: String = "")

data class CryptoCompareCoinDetail(@SerializedName("Id")
                                   @Expose
                                   val id: String = "",
                                   @SerializedName("Name")
                                   @Expose
                                   val name: String = "",
                                   @SerializedName("Symbol")
                                   @Expose
                                   val symbol: String = "",
                                   @SerializedName("CoinName")
                                   @Expose
                                   val coinName: String = "",
                                   @SerializedName("FullName")
                                   @Expose
                                   val fullName: String = "",
                                   @SerializedName("TotalCoinSupply")
                                   @Expose
                                   val totalSupply: String = "")

data class CryptoCompareTopCoinPairsResponse(@SerializedName("Response")
                                             @Expose
                                             val response: String = "",
                                             @SerializedName("Data")
                                             @Expose
                                             val data: List<TopCoinPair> = listOf())


data class CryptoComparePriceHistoricalResponse(val historicalPairs: Map<String, Map<String, Double>> = mapOf())