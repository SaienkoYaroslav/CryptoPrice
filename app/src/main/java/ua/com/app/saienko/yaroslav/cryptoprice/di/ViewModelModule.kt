package ua.com.app.saienko.yaroslav.cryptoprice.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ua.com.app.saienko.yaroslav.cryptoprice.presentation.CoinViewModel

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(CoinViewModel::class)
    @Binds
    fun bindCoinViewModule(impl: CoinViewModel): ViewModel

}