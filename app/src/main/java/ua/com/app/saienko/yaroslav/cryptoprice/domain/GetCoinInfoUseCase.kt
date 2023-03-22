package ua.com.app.saienko.yaroslav.cryptoprice.domain

class GetCoinInfoUseCase(
    private val coinRepository: CoinRepository
) {

    operator fun invoke(fromSymbol: String) = coinRepository.getCoinInfo(fromSymbol)

}