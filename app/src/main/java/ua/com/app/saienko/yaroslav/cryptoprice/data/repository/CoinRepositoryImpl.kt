package ua.com.app.saienko.yaroslav.cryptoprice.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.delay
import ua.com.app.saienko.yaroslav.cryptoprice.data.database.AppDatabase
import ua.com.app.saienko.yaroslav.cryptoprice.data.mapper.CoinMapper
import ua.com.app.saienko.yaroslav.cryptoprice.data.network.ApiFactory
import ua.com.app.saienko.yaroslav.cryptoprice.domain.CoinInfo
import ua.com.app.saienko.yaroslav.cryptoprice.domain.CoinRepository

class CoinRepositoryImpl(
    private val application: Application
): CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val apiService = ApiFactory.apiService

    private val mapper = CoinMapper()

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

    override suspend fun loadDate() {
        while (true) {
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            val fSymbols = mapper.mapNamesListDtoToString(topCoins)
            val jsonContainer = apiService.getFullPriceList(fSyms = fSymbols)
            val coinInfoDtoList = mapper.mapJsonContainerDtoToListCoinInfo(jsonContainer)
            val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
            coinInfoDao.insertPriceList(dbModelList)
            delay(10000)
        }
    }
}