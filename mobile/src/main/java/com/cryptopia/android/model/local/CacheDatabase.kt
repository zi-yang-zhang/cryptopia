package com.cryptopia.android.model.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by robertzzy on 07/12/17.
 */
@Database(entities = arrayOf(SuggestionPricePair::class, TopCoinPair::class), version = 1) abstract class CacheDatabase : RoomDatabase() {
    abstract fun pricePairDAO(): PricePairDAO
    abstract fun coinPairDao(): CoinPairDao

}