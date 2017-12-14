package com.cryptopia.android.di.configuration

import android.content.Context
import com.cryptopia.android.BuildConfig
import com.facebook.drawee.backends.pipeline.Fresco
import timber.log.Timber

/**
 * Created by robertzzy on 11/12/17.
 */
class TimberConfiguration : ApplicationConfiguration {
    override fun configure() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        else Timber.plant(Timber.asTree())
    }

}

class FrescoConfiguration(val context: Context) : ApplicationConfiguration {

    override fun configure() {
        Fresco.initialize(context)
    }

}