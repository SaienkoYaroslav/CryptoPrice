package ua.com.app.saienko.yaroslav.cryptoprice.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ua.com.app.saienko.yaroslav.cryptoprice.data.model.CoinInfoListOfData
import ua.com.app.saienko.yaroslav.cryptoprice.data.model.CoinPriceInfoRawData

interface ApiService {

    // ендпоінт
    @GET("top/totalvolfull")
    // отримати список популярних валют
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): Single<CoinInfoListOfData>

    // ендпоінт
    @GET("pricemultifull")
    // отримати повну інформацію
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_FROM_SYMBOL) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY
    ): Single<CoinPriceInfoRawData>

    // константи
    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"

        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOL = "fsyms"

        private const val CURRENCY = "USD"

    }
}