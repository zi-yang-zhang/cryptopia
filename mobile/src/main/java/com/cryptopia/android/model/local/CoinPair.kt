package com.cryptopia.android.model.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.Flowable

/**
 * Created by robertzzy on 07/12/17.
 */


@Entity(tableName = "top_coin_pairs", primaryKeys = arrayOf("fromSymbol", "toSymbol"))
data class TopCoinPair(@PrimaryKey(autoGenerate = true) val id: Int,
                       @SerializedName("exchange")
                       @Expose
                       val exchange: String = "",
                       @SerializedName("fromSymbol")
                       @Expose
                       val fromSymbol: String = "",
                       @SerializedName("toSymbol")
                       @Expose
                       val toSymbol: String = "",
                       @SerializedName("volume24h")
                       @Expose
                       val volume24h: Double = 0.0,
                       @SerializedName("volume24hTo")
                       @Expose
                       val volume24hTo: Double = 0.0)

@Database(entities = arrayOf(TopCoinPair::class), version = 1) abstract class CoinPairCacheDatabase : RoomDatabase() {
    abstract fun coinPairDao(): CoinPairDao

}

interface CoinPairDao {
    @Query("SELECT * FROM top_coin_pairs")
    fun getTopCoinPairs(): LiveData<List<TopCoinPair>>

    @Query("DELETE * FROM top_coin_pairs")
    fun clearTopCoinPairs(): Flowable<Int>

    @Insert
    fun addTopCoinPairs(topCoinPairs: List<TopCoinPair>)
}

