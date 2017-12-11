package com.cryptopia.android.ui.suggestion

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by robertzzy on 07/12/17.
 */

@Module
abstract class SuggestionModule {

    @ContributesAndroidInjector
    abstract fun coinSuggestionFragment(): CoinSuggestionFragment


}