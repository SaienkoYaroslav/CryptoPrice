package ua.com.app.saienko.yaroslav.cryptoprice.data.network.model

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoJsonContainerDto (
    @SerializedName("RAW")
    @Expose
    val json: JsonObject? = null
        )