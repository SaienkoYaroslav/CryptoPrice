package ua.com.app.saienko.yaroslav.cryptoprice.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.com.app.saienko.yaroslav.cryptoprice.data.model.CoinPriceInfo

@Dao
interface CoinPriceInfoDao {
    // Запрос для метода нижче
    @Query("SELECT * FROM full_price_list ORDER BY price DESC")
    // метод для відображення списку валют в RecycleView
    fun getPriceList(): LiveData<List<CoinPriceInfo>>

    // метод для повернення однієї валюти
    @Query("SELECT * FROM full_price_list WHERE fromSymbol == :fSym LIMIT 1")
    fun getPriceInfoAboutCoin(fSym: String): LiveData<CoinPriceInfo>

    // метод для збереження отриманих даних з інтернету в базу
    @Insert(onConflict = OnConflictStrategy.REPLACE) // кожного разу коли приходять нові дані, старі - заміняються
    fun insertPriceList(priceList: List<CoinPriceInfo>)
}