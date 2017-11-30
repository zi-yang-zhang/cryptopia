package com.cryptopia.android

import android.app.Application
import android.arch.persistence.room.Room
import com.cryptopia.android.model.local.PriceCacheDatabase
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
    fun priceCacheDatabase(context: Application): PriceCacheDatabase =
            Room.databaseBuilder(context, PriceCacheDatabase::class.java, BuildConfig.CACHE_DATABASE_NAME).build()

}