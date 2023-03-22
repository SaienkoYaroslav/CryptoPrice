package ua.com.app.saienko.yaroslav.cryptoprice.domain

import androidx.lifecycle.LiveData

class GetCoinInfoListUseCase(private val coinRepository: CoinRepository) {

    operator fun invoke() = coinRepository.getCoinInfoList()


}