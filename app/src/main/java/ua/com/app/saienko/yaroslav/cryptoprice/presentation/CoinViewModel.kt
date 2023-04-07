package ua.com.app.saienko.yaroslav.cryptoprice.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ua.com.app.saienko.yaroslav.cryptoprice.data.repository.CoinRepositoryImpl
import ua.com.app.saienko.yaroslav.cryptoprice.domain.GetCoinInfoListUseCase
import ua.com.app.saienko.yaroslav.cryptoprice.domain.GetCoinInfoUseCase
import ua.com.app.saienko.yaroslav.cryptoprice.domain.LoadDateUseCase

// AndroidViewModel параметром потрібно передати Application
class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDateUseCase = LoadDateUseCase(repository)

    // Об'єкт LiveData на який будемо підписуватись
    val coinInfoList = getCoinInfoListUseCase()

    // метод, який повертає інформацію про одну валюту
    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
        loadDateUseCase()
    }

}

