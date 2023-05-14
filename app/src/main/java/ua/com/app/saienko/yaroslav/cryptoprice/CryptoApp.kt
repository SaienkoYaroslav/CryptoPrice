package ua.com.app.saienko.yaroslav.cryptoprice

import android.app.Application
import androidx.work.Configuration
import ua.com.app.saienko.yaroslav.cryptoprice.data.workers.CoinWorkerFactory
import ua.com.app.saienko.yaroslav.cryptoprice.di.DaggerApplicationComponent
import javax.inject.Inject

class CryptoApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CoinWorkerFactory

    val component by lazy {
        DaggerApplicationComponent
            .factory()
            .create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

}