package com.cryptopia.android.model.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by robertzzy on 07/12/17.
 */

const val TOP_COIN_PAIR_TABLE: String = "coin_pair"


@Entity(tableName = TOP_COIN_PAIR_TABLE)
data class TopCoinPair(@PrimaryKey(autoGenerate = true) val id: Int,
                       @SerializedName("exchange")
                       @Expose
                       val indexMarket: String = "",
                       @SerializedName("fromSymbol")
                       @Expose
                       val fromCoin: String = "",
                       @SerializedName("toSymbol")
                       @Expose
                       val toCoin: String = "",
                       @SerializedName("volume24h")
                       @Expose
                       val volume24h: Double = 0.0,
                       @SerializedName("volume24hTo")
                       @Expose
                       val volume24hTo: Double = 0.0)

@Dao
interface CoinPairDao {
    @Query("SELECT * FROM $TOP_COIN_PAIR_TABLE")
    fun getTopCoinPairs(): LiveData<List<TopCoinPair>>

    @Query("DELETE FROM $TOP_COIN_PAIR_TABLE")
    fun clearTopCoinPairs()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(topCoinPairs: List<TopCoinPair>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(topCoinPairs: List<TopCoinPair>): Int
}

