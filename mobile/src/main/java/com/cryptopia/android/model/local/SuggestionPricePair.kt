package com.cryptopia.android.model.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


/**
 * Created by robertzzy on 18/11/17.
 */

const val SUGGESTION_PRICE_PAIR_TABLE: String = "suggestion_price_pairs"

@Entity(tableName = SUGGESTION_PRICE_PAIR_TABLE, primaryKeys = ["fromCoin", "toCoin"])
data class SuggestionPricePair(val fromCoin: String,
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
    @Query("SELECT * FROM " + SUGGESTION_PRICE_PAIR_TABLE)
    fun getAllSuggestionPricePairs(): LiveData<List<SuggestionPricePair>>

    @Query("SELECT * FROM $SUGGESTION_PRICE_PAIR_TABLE WHERE fromCoin IN (:fromCoin) AND toCoin IN (:toCoin)")
    fun getSuggestionPricePairs(fromCoin: List<String>, toCoin: List<String>): LiveData<List<SuggestionPricePair>>

    @Query("SELECT * FROM $SUGGESTION_PRICE_PAIR_TABLE WHERE fromCoin IN (:fromCoin) AND toCoin IN (:toCoin) AND indexMarket LIKE :indexMarket")
    fun getSuggestionPricePairs(fromCoin: List<String>, toCoin: List<String>, indexMarket: String): LiveData<List<SuggestionPricePair>>

    @Query("SELECT * FROM $SUGGESTION_PRICE_PAIR_TABLE WHERE fromCoin IN (:fromCoin)")
    fun getSuggestionPricePairs(fromCoin: List<String>): LiveData<List<SuggestionPricePair>>

    @Query("SELECT * FROM $SUGGESTION_PRICE_PAIR_TABLE " +
            "INNER JOIN $TOP_COIN_PAIR_TABLE ON $TOP_COIN_PAIR_TABLE.fromCoin = $SUGGESTION_PRICE_PAIR_TABLE.fromCoin " +
            "AND $TOP_COIN_PAIR_TABLE.toCoin = $SUGGESTION_PRICE_PAIR_TABLE.toCoin")
    fun getTopPricePairs(): LiveData<List<SuggestionPricePair>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(suggestionPricePair: SuggestionPricePair): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(suggestionPricePairs: List<SuggestionPricePair>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(suggestionPricePairs: List<SuggestionPricePair>): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(suggestionPricePairs: SuggestionPricePair): Int


    @Delete
    fun removeCache(suggestionPricePairs: SuggestionPricePair): Int
}