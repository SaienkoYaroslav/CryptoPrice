package ua.com.app.saienko.yaroslav.cryptoprice.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ua.com.app.saienko.yaroslav.cryptoprice.presentation.CoinDetailFragment
import ua.com.app.saienko.yaroslav.cryptoprice.presentation.CoinPriceListActivity

@ApplicationScope
@Component(modules = [DomainModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: CoinPriceListActivity)

    fun inject(fragment: CoinDetailFragment)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application
        ) : ApplicationComponent
    }

}