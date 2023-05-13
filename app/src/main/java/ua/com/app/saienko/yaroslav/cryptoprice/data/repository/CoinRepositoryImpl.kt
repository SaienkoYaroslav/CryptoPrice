package ua.com.app.saienko.yaroslav.cryptoprice.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import ua.com.app.saienko.yaroslav.cryptoprice.data.database.CoinInfoDao
import ua.com.app.saienko.yaroslav.cryptoprice.data.mapper.CoinMapper
import ua.com.app.saienko.yaroslav.cryptoprice.data.workers.RefreshDataWorker
import ua.com.app.saienko.yaroslav.cryptoprice.domain.CoinInfo
import ua.com.app.saienko.yaroslav.cryptoprice.domain.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val application: Application,
    private val mapper: CoinMapper,
    private val coinInfoDao: CoinInfoDao
): CoinRepository {

    //private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()


    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()){
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override fun loadDate() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
            )
    }
}