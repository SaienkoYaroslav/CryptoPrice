package ua.com.app.saienko.yaroslav.cryptoprice.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ua.com.app.saienko.yaroslav.cryptoprice.data.database.CoinInfoDao
import ua.com.app.saienko.yaroslav.cryptoprice.data.mapper.CoinMapper
import ua.com.app.saienko.yaroslav.cryptoprice.data.network.ApiService

class RefreshDataWorkerFactory(
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService,
    private val mapper: CoinMapper
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return RefreshDataWorker(
            appContext,
            workerParameters,
            coinInfoDao,
            apiService,
            mapper
        )
    }
}