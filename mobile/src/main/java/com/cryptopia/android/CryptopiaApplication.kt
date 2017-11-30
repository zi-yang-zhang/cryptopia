package com.cryptopia.android

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created by robertzzy on 29/11/17.
 */
class CryptopiaApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerCoreComponent.builder().application(this).build()


}