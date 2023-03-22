package ua.com.app.saienko.yaroslav.cryptoprice.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.com.app.saienko.yaroslav.cryptoprice.data.network.ApiFactory
import ua.com.app.saienko.yaroslav.cryptoprice.data.database.AppDatabase
import ua.com.app.saienko.yaroslav.cryptoprice.data.model.CoinPriceInfo
import ua.com.app.saienko.yaroslav.cryptoprice.data.model.CoinPriceInfoRawData
import java.util.concurrent.TimeUnit

// AndroidViewModel параметром потрібно передати Application
class CoinViewModel(application: Application) : AndroidViewModel(application) {

    // Екземпляр бази даних. Передаємо application в якості контексту
    private val db = AppDatabase.getInstance(application)

    // Об'єкт CompositeDisposable, який задіспосим в методі onCleared()
    private val compositeDisposable = CompositeDisposable()

    // Об'єкт LiveData на який будемо підписуватись
    val priceList = db.coinPriceInfoDao().getPriceList()

    // метод, який повертає інформацію про одну валюту
    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }

    // Додаємо блок ініціалізації (Особливість Котлін). Код який вказується в {} викликається автоматично
    // при створенні цього об'єкту
    init {
        loadData()
    }

    // Метод, який завантажує дані з мережі
    fun loadData() {
        // стартується завантаження найпопулярніших валют
        val disposable =
            ApiFactory.apiService.getTopCoinsInfo(limit = 50)
                .map {
                    it.data?.map { it.coinInfo?.name }?.joinToString(",")// Отримані дані перетворюються в одну строку
                }
                .flatMap {
                    ApiFactory.apiService.getFullPriceList(fSyms = it)// Отримана строка передається всередину блока, як it і по ній отримуємо повний прайс лист
                }
                .map { getPriceListFromRawData(it) }
                .delaySubscription(10, TimeUnit.SECONDS) // інтервал, через який буде виконуватись повторне завантаження
                .repeat() // завантаження буде виконуватись постійно (при успіху)
                .retry() // при помилці
                .subscribeOn(Schedulers.io())
                .subscribe({
                    // додаємо дані в базу даних
                    db.coinPriceInfoDao().insertPriceList(it)
                    Log.d("TEST_OF_LOADING_DATA", "Success: $it")
                }, {
                    Log.d("TEST_OF_LOADING_DATA", "Failure: ${it.message}")
                })
    }

    // метод який перетворює об'єкт it з .subscribe({ it: CoinPriceInfoRawData! в ліст CoinPriceInfo
    private fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawData): List<CoinPriceInfo> {
        // На вхід приходить coinPriceInfoRawData: CoinPriceInfoRawData в якому є json об'єкт, який потрібно
        // розпарсити вручну
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        // отримуємо всі ключі
        val coinKeySet = jsonObject.keySet()
        // проходимось по всіх ключам і отримуємо вкладені json-об'єкти
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                // додаємо отриманий об'єкт в колекцію
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}