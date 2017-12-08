package com.cryptopia.android.di

import com.cryptopia.android.repository.CoinRepository
import com.cryptopia.android.repository.CoinRepositoryImpl
import com.cryptopia.android.repository.PriceRepository
import com.cryptopia.android.repository.PriceRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by robertzzy on 29/11/17.
 */

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providePriceRepository(impl: PriceRepositoryImpl): PriceRepository

    @Singleton
    @Binds
    abstract fun provideCoinRepository(impl: CoinRepositoryImpl): CoinRepository
}