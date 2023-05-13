package ua.com.app.saienko.yaroslav.cryptoprice.presentation

import androidx.lifecycle.ViewModel
import ua.com.app.saienko.yaroslav.cryptoprice.domain.GetCoinInfoListUseCase
import ua.com.app.saienko.yaroslav.cryptoprice.domain.GetCoinInfoUseCase
import ua.com.app.saienko.yaroslav.cryptoprice.domain.LoadDateUseCase
import javax.inject.Inject

// AndroidViewModel параметром потрібно передати Application
class CoinViewModel @Inject constructor(
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val loadDateUseCase: LoadDateUseCase
) : ViewModel() {


    // Об'єкт LiveData на який будемо підписуватись
    val coinInfoList = getCoinInfoListUseCase()

    // метод, який повертає інформацію про одну валюту
    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
        loadDateUseCase()
    }

}

