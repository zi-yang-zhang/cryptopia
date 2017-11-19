package com.cryptopia.android.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by ziyang.zhang on 2017-11-18.
 */


data class CryptoComparePriceResponse(@SerializedName("RAW")
                                      @Expose
                                      val rawData: Map<String, Map<String, CryptoComparePriceResponseRawPriceDetail>> = HashMap(),
                                      @SerializedName("DISPLAY")
                                      @Expose
                                      val displayData: Map<String, Map<String, CryptoComparePriceResponseDisplayPriceDetail>> = HashMap())

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
                                                    val price: Long = 0L)

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
                                                        val price: String = "")