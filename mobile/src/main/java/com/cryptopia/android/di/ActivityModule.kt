package com.cryptopia.android.di

import com.cryptopia.android.ui.suggestion.SuggestionActivity
import com.cryptopia.android.ui.suggestion.SuggestionModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by robertzzy on 30/11/17.
 */

@Module
abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(SuggestionModule::class))
    abstract fun suggestionActivity(): SuggestionActivity
}