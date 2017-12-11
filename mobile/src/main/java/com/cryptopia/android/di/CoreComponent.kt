package com.cryptopia.android.di

import android.app.Application
import com.cryptopia.android.CryptopiaApplication
import com.cryptopia.android.model.local.CacheDatabase
import com.cryptopia.android.network.CryptoCompareAPI
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Created by robertzzy on 29/11/17.
 */

@Singleton
@Component(modules = arrayOf(NetworkModule::class,
        ConfigModule::class,
        DataBaseModule::class,
        AndroidModule::class,
        RepositoryModule::class,
        ActivityModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class))
interface CoreComponent : AndroidInjector<CryptopiaApplication> {
    fun database(): CacheDatabase
    fun cryptoCompareApi(): CryptoCompareAPI
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): CoreComponent
    }
}