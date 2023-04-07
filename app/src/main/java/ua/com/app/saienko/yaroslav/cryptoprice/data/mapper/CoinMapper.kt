package ua.com.app.saienko.yaroslav.cryptoprice.data.mapper

import com.google.gson.Gson
import ua.com.app.saienko.yaroslav.cryptoprice.data.database.CoinInfoDbModel
import ua.com.app.saienko.yaroslav.cryptoprice.data.network.model.CoinInfoDto
import ua.com.app.saienko.yaroslav.cryptoprice.data.network.model.CoinInfoJsonContainerDto
import ua.com.app.saienko.yaroslav.cryptoprice.data.network.model.CoinNamesListDto
import ua.com.app.saienko.yaroslav.cryptoprice.domain.CoinInfo
import java.sql.Date
import java.sql.Timestamp
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class CoinMapper {

    fun mapDtoToDbModel(coinInfoDto: CoinInfoDto) = CoinInfoDbModel(
        fromSymbol = coinInfoDto.fromSymbol,
        toSymbol = coinInfoDto.toSymbol,
        price = coinInfoDto.price,
        lastUpdate = coinInfoDto.lastUpdate,
        highDay = coinInfoDto.highDay,
        lowDay = coinInfoDto.lowDay,
        lastMarket = coinInfoDto.lastMarket,
        imageUrl = BASE_IMAGE_URL + coinInfoDto.imageUrl
    )

    fun mapJsonContainerDtoToListCoinInfo(jsonContainerDto: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        // На вхід приходить coinPriceInfoRawData: CoinPriceInfoRawData в якому є json об'єкт, який потрібно
        // розпарсити вручну
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainerDto.json ?: return result
        // отримуємо всі ключі
        val coinKeySet = jsonObject.keySet()
        // проходимось по всіх ключам і отримуємо вкладені json-об'єкти
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                // додаємо отриманий об'єкт в колекцію
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListDtoToString(namesListDto: CoinNamesListDto): String {
        val string = namesListDto.names?.map { it.coinName?.name }?.joinToString(",")
        return string ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinInfo {
        return CoinInfo(
            fromSymbol = dbModel.fromSymbol,
            toSymbol = dbModel.toSymbol,
            price = formatFloatToString(dbModel.price),
            lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
            highDay = dbModel.highDay,
            lowDay = dbModel.lowDay,
            lastMarket = dbModel.lastMarket,
            imageUrl = dbModel.imageUrl
        )
    }

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        // в нашому випадку приходять секунди, а метод працює з мілісек, то множимо на 1000
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        // паттерн з годинами, хв і сек.
        val pattern = "HH:mm:ss"
        // час по Грінвічу
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        // час в нашій зоні
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    private fun formatFloatToString(price: Float?): String {
        val formattedFloat = DecimalFormat("#0.00000000")
        return if (price?.compareTo(0.001) == -1) {
            formattedFloat.format(price)
        } else {
            price.toString()
        }
    }

    companion object {
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }


}