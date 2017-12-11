package com.cryptopia.android.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Created by robertzzy on 11/12/17.
 */
@Module
class ConfigModule {


    @Provides
    @IntoSet
    @Singleton
    fun timberConfiguration(): ApplicationConfiguration = TimberConfiguration()


    @Provides
    @IntoSet
    @Singleton
    fun frescoConfiguration(context: Context): ApplicationConfiguration = FrescoConfiguration(context)
}
