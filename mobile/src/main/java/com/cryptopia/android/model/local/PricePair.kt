package com.cryptopia.android.model.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import java.util.*


/**
 * Created by robertzzy on 18/11/17.
 */

data class PricePairs(val from: String, val fromSymbol: String, val pairs: List<PricePair>)

@Entity(tableName = "price_pairs_cache", primaryKeys = arrayOf("from", "to"))
data class PricePair(val from: String, val to: String, val price: Double, val displayPrice: String, val toSymbol: String, val fromSymbol: String, val market: String, @TypeConverters(TimeConverters::class) val updatedAt: Date)


@Database(entities = arrayOf(PricePair::class), version = 1) abstract class PriceCacheDatabase : RoomDatabase() {
    abstract fun pricePairDAO(): PricePairDAO
}

interface PricePairDAO {
    @Query("SELECT * FROM price_pairs_cache")
    fun getAllPricePairs(): LiveData<List<PricePair>>

    @Query("SELECT * FROM price_pairs_cache WHERE from IN (:from) AND to IN (:to)")
    fun getPricePairs(from: List<String>, to: List<String>): LiveData<List<PricePair>>

    @Query("SELECT * FROM price_pairs_cache WHERE from IN (:from) AND to IN (:to) AND market LIKE :market")
    fun getPricePairs(from: List<String>, to: List<String>, market: String): LiveData<List<PricePair>>

    @Query("SELECT * FROM price_pairs_cache WHERE from IN (:from)")
    fun getPricePairs(from: List<String>): LiveData<List<PricePair>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun cachePricePair(pricePair: PricePair)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun cachePricePairs(pricePairs: List<PricePair>)

    @Delete
    fun removeCache(pricePairs: PricePair)
}