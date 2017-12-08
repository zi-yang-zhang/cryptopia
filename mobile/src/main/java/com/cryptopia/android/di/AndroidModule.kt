package com.cryptopia.android.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/**
 * Created by robertzzy on 29/11/17.
 */
@Module
abstract class AndroidModule {

    @Binds
    abstract fun context(context: Application): Context
}