package com.cryptopia.android

import com.cryptopia.android.di.ApplicationConfiguration
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

/**
 * Created by robertzzy on 29/11/17.
 */
class CryptopiaApplication : DaggerApplication() {
    @Inject lateinit var configs: Set<@JvmSuppressWildcards ApplicationConfiguration>

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerCoreComponent.builder().application(this).build()


    override fun onCreate() {
        super.onCreate()
        configs.forEach { it.configure() }
    }
}