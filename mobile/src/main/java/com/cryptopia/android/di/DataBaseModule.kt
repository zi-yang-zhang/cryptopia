package com.cryptopia.android.di

import android.app.Application
import android.arch.persistence.room.Room
import com.cryptopia.android.BuildConfig
import com.cryptopia.android.model.local.CacheDatabase
import com.cryptopia.android.model.local.CoinPairDao
import com.cryptopia.android.model.local.PricePairDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by robertzzy on 29/11/17.
 */

@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun priceCacheDatabase(context: Application): CacheDatabase =
            Room.databaseBuilder(context, CacheDatabase::class.java, BuildConfig.CACHE_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun coinPairsDAO(database: CacheDatabase): CoinPairDao = database.coinPairDao()

    @Singleton
    @Provides
    fun priceListDAO(database: CacheDatabase): PricePairDAO = database.pricePairDAO()
}