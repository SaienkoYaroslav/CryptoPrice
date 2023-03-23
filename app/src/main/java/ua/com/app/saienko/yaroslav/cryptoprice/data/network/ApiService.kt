package ua.com.app.saienko.yaroslav.cryptoprice.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ua.com.app.saienko.yaroslav.cryptoprice.data.network.model.CoinNamesListDto
import ua.com.app.saienko.yaroslav.cryptoprice.data.network.model.CoinInfoJsonContainerDto

interface ApiService {

    // ендпоінт
    @GET("top/totalvolfull")
    // отримати список популярних валют
    suspend fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): CoinNamesListDto

    // ендпоінт
    @GET("pricemultifull")
    // отримати повну інформацію
    suspend fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_FROM_SYMBOL) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY
    ): CoinInfoJsonContainerDto

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