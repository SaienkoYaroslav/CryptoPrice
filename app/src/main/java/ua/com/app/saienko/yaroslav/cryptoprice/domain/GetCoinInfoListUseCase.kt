package ua.com.app.saienko.yaroslav.cryptoprice.domain

import javax.inject.Inject

class GetCoinInfoListUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {

    operator fun invoke() = coinRepository.getCoinInfoList()


}