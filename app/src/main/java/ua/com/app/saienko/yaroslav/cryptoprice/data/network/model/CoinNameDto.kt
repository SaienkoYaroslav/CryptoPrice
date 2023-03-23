package ua.com.app.saienko.yaroslav.cryptoprice.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// В Котлін прийнято, що всі об'єкти pojo являються дата-класами
data class CoinNameDto(

    // поля мають бути паблік (alt + j)
    @SerializedName("Name")
    @Expose
    val name: String? = null

)