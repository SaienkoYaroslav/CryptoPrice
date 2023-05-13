package ua.com.app.saienko.yaroslav.cryptoprice.domain

import javax.inject.Inject

class GetCoinInfoUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {

    operator fun invoke(fromSymbol: String) = coinRepository.getCoinInfo(fromSymbol)

}