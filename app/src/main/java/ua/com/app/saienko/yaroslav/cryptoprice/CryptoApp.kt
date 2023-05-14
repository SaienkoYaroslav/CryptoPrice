package ua.com.app.saienko.yaroslav.cryptoprice

import android.app.Application
import androidx.work.Configuration
import ua.com.app.saienko.yaroslav.cryptoprice.data.database.AppDatabase
import ua.com.app.saienko.yaroslav.cryptoprice.data.mapper.CoinMapper
import ua.com.app.saienko.yaroslav.cryptoprice.data.network.ApiFactory
import ua.com.app.saienko.yaroslav.cryptoprice.data.workers.RefreshDataWorkerFactory
import ua.com.app.saienko.yaroslav.cryptoprice.di.DaggerApplicationComponent

class CryptoApp : Application(), Configuration.Provider {

    val component by lazy {
        DaggerApplicationComponent
            .factory()
            .create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(
                RefreshDataWorkerFactory(
                    AppDatabase.getInstance(this).coinPriceInfoDao(),
                    ApiFactory.apiService,
                    CoinMapper()
                )
            )
            .build()
    }

}