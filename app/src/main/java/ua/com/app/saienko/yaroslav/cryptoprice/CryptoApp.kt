package ua.com.app.saienko.yaroslav.cryptoprice

import android.app.Application
import ua.com.app.saienko.yaroslav.cryptoprice.di.DaggerApplicationComponent

class CryptoApp: Application() {

    val component by lazy {
        DaggerApplicationComponent
            .factory()
            .create(this)
    }

}