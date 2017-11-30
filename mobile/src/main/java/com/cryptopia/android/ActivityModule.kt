package com.cryptopia.android

import com.cryptopia.android.pricelist.PriceListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by robertzzy on 30/11/17.
 */

@Module
abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf())
    abstract fun tasksActivity(): PriceListActivity
}