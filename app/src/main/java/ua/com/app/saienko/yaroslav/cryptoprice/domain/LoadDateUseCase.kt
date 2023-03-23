package ua.com.app.saienko.yaroslav.cryptoprice.domain

class LoadDateUseCase(
    private val repository: CoinRepository
    ) {

    suspend operator fun invoke() = repository.loadDate()
}