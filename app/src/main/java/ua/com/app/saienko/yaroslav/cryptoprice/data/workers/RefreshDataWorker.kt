package ua.com.app.saienko.yaroslav.cryptoprice.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import ua.com.app.saienko.yaroslav.cryptoprice.data.database.CoinInfoDao
import ua.com.app.saienko.yaroslav.cryptoprice.data.mapper.CoinMapper
import ua.com.app.saienko.yaroslav.cryptoprice.data.network.ApiService

class RefreshDataWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService,
    private val mapper: CoinMapper
) :
    CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fSymbols = mapper.mapNamesListDtoToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fSyms = fSymbols)
                val coinInfoDtoList = mapper.mapJsonContainerDtoToListCoinInfo(jsonContainer)
                val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }

    companion object {

        const val WORK_NAME = "worker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>()
                .build()
        }

    }


}