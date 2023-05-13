package ua.com.app.saienko.yaroslav.cryptoprice.di


import android.app.Application
import dagger.Module
import dagger.Provides
import ua.com.app.saienko.yaroslav.cryptoprice.data.database.AppDatabase
import ua.com.app.saienko.yaroslav.cryptoprice.data.database.CoinInfoDao

@Module
class DataModule {

    @Provides
    fun provideCoinInfoDao(
        application: Application
    ): CoinInfoDao {
        return AppDatabase.getInstance(application).coinPriceInfoDao()
    }

}