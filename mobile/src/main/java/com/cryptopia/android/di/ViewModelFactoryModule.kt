package com.cryptopia.android.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.cryptopia.android.di.vm.ViewModelBinding
import com.cryptopia.android.di.vm.ViewModelFactory
import com.cryptopia.android.ui.suggestion.CoinSuggestionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by robertzzy on 10/12/17.
 */
@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory):
            ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelBinding(CoinSuggestionViewModel::class)
    abstract fun coinSuggestionViewModel(vm: CoinSuggestionViewModel): ViewModel
}