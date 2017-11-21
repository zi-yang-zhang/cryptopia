package com.cryptopia.android.model.local

/**
 * Created by robertzzy on 18/11/17.
 */

data class PricePairs(val from: String, val fromSymbol: String, val pairs: List<PricePair>)

data class PricePair(val to: String, val price: Double, val displayPrice: String, val toSymbol: String, val market: String)
