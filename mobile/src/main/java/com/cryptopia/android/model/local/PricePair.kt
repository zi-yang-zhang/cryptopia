package com.cryptopia.android.model.local

/**
 * Created by robertzzy on 18/11/17.
 */

data class PricePairs(val from: String = "", var fromSymbol: String = "", val pairs: MutableList<PricePair> = ArrayList())

data class PricePair(val to: String, var price: Long, var displayPrice: String = "", var toSymbol: String = "", var market: String = "")
