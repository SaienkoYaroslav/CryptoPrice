package ua.com.app.saienko.yaroslav.cryptoprice.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// В Котлін прийнято, що всі об'єкти pojo являються дата-класами
data class CoinInfo(

    // поля мають бути паблік (alt + j)
    @SerializedName("Name")
    @Expose
    val name: String? = null

)