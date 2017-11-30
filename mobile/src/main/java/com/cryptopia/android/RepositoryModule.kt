package com.cryptopia.android

import com.cryptopia.android.model.local.PriceCacheDatabase
import com.cryptopia.android.network.CryptoCompareAPI
import com.cryptopia.android.repository.CoinRepository
import com.cryptopia.android.repository.CoinRepositoryImpl
import com.cryptopia.android.repository.PriceRepository
import com.cryptopia.android.repository.PriceRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by robertzzy on 29/11/17.
 */

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun providePriceRepository(api: CryptoCompareAPI, database: PriceCacheDatabase): PriceRepository = PriceRepositoryImpl(api, database.pricePairDAO())

    @Singleton
    @Provides
    fun provideCoinRepository(api: CryptoCompareAPI): CoinRepository = CoinRepositoryImpl(api)
}