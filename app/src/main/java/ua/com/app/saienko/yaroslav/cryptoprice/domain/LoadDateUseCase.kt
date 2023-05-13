package ua.com.app.saienko.yaroslav.cryptoprice.domain

import javax.inject.Inject

class LoadDateUseCase @Inject constructor(
    private val repository: CoinRepository
    ) {

    operator fun invoke() = repository.loadDate()
}