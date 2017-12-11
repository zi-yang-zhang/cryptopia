package com.cryptopia.android.model.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


/**
 * Created by robertzzy on 18/11/17.
 */

const val PRICE_PAIR_TABLE: String = "price_pairs"

@Entity(tableName = PRICE_PAIR_TABLE, primaryKeys = ["fromCoin", "toCoin"])
data class PricePair(val fromCoin: String,
                     val toCoin: String,
                     val price: Double,
                     val displayPrice: String,
                     val fromCoinDisplay: String,
                     val toCoinDisplay: String,
                     val indexMarket: String,
                     @TypeConverters(TimeConverters::class) val updatedAt: Long,
                     val exchange: String,
                     val exchangeSymbol: String,
                     val changePercentageOfDay: Double,
                     val changeOfDay: Double)

@Dao
interface PricePairDAO {
    @Query("SELECT * FROM " + PRICE_PAIR_TABLE)
    fun getAllPricePairs(): LiveData<List<PricePair>>

    @Query("SELECT * FROM $PRICE_PAIR_TABLE WHERE fromCoin IN (:fromCoin) AND toCoin IN (:toCoin)")
    fun getPricePairs(fromCoin: List<String>, toCoin: List<String>): LiveData<List<PricePair>>

    @Query("SELECT * FROM $PRICE_PAIR_TABLE WHERE fromCoin IN (:fromCoin) AND toCoin IN (:toCoin) AND indexMarket LIKE :indexMarket")
    fun getPricePairs(fromCoin: List<String>, toCoin: List<String>, indexMarket: String): LiveData<List<PricePair>>

    @Query("SELECT * FROM $PRICE_PAIR_TABLE WHERE fromCoin IN (:fromCoin)")
    fun getPricePairs(fromCoin: List<String>): LiveData<List<PricePair>>

    @Query("SELECT * FROM $PRICE_PAIR_TABLE " +
            "INNER JOIN $COIN_PAIR_TABLE ON $COIN_PAIR_TABLE.fromCoin = $PRICE_PAIR_TABLE.fromCoin " +
            "INNER JOIN $COIN_PAIR_TABLE ON $COIN_PAIR_TABLE.toCoin = $PRICE_PAIR_TABLE.toCoin")
//  TODO: fix query
    fun getTopPricePairs(): LiveData<List<PricePair>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun cachePricePair(pricePair: PricePair)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun cachePricePairs(pricePairs: List<PricePair>)

    @Delete
    fun removeCache(pricePairs: PricePair)
}