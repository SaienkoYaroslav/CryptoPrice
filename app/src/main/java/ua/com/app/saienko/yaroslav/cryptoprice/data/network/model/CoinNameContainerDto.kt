package ua.com.app.saienko.yaroslav.cryptoprice.data.network.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CoinNameContainerDto (
    @SerializedName("CoinInfo")
    @Expose
    val coinName: CoinNameDto? = null
)