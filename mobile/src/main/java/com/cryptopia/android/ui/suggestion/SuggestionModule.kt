package com.cryptopia.android.ui.suggestion

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.cryptopia.android.di.FragmentScoped
import com.cryptopia.android.di.ViewModelBinding
import com.cryptopia.android.di.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by robertzzy on 07/12/17.
 */

@Module
abstract class SuggestionModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun coinSuggestionFragment(): CoinSuggestionFragment

    @FragmentScoped
    @IntoMap
    @ViewModelBinding(CoinSuggestionViewModel::class)
    abstract fun coinSuggestionViewModel(vm: CoinSuggestionViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory):
            ViewModelProvider.Factory
}