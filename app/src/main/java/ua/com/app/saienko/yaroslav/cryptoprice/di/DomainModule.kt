package ua.com.app.saienko.yaroslav.cryptoprice.di

import dagger.Binds
import dagger.Module
import ua.com.app.saienko.yaroslav.cryptoprice.data.repository.CoinRepositoryImpl
import ua.com.app.saienko.yaroslav.cryptoprice.domain.CoinRepository

@Module
interface DomainModule {

    @Binds
    fun bindRepository(impl: CoinRepositoryImpl) : CoinRepository

}