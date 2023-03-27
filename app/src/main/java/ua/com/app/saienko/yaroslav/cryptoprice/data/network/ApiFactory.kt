package ua.com.app.saienko.yaroslav.cryptoprice.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// сінглтон
object ApiFactory {

    // константи
    private const val BASE_URL = "https://min-api.cryptocompare.com/data/"


    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}