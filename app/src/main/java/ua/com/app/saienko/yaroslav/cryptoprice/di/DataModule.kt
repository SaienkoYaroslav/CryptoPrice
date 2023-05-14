package ua.com.app.saienko.yaroslav.cryptoprice.di


import android.app.Application
import dagger.Module
import dagger.Provides
import ua.com.app.saienko.yaroslav.cryptoprice.data.database.AppDatabase
import ua.com.app.saienko.yaroslav.cryptoprice.data.database.CoinInfoDao
import ua.com.app.saienko.yaroslav.cryptoprice.data.network.ApiFactory
import ua.com.app.saienko.yaroslav.cryptoprice.data.network.ApiService

@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideCoinInfoDao(
        application: Application
    ): CoinInfoDao {
        return AppDatabase.getInstance(application).coinPriceInfoDao()
    }

    @ApplicationScope
    @Provides
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }

}