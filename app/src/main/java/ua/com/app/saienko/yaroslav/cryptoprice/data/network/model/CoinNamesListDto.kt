package ua.com.app.saienko.yaroslav.cryptoprice.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinNamesListDto(
    @SerializedName("Data")
    @Expose
    val names: List<CoinNameContainerDto>? = null
)