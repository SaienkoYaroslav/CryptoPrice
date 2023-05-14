package ua.com.app.saienko.yaroslav.cryptoprice.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ua.com.app.saienko.yaroslav.cryptoprice.data.workers.ChildWorkerFactory
import ua.com.app.saienko.yaroslav.cryptoprice.data.workers.RefreshDataWorker

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshDataWorker::class)
    fun bindRefreshDataWorkerFactory(worker: RefreshDataWorker.Factory): ChildWorkerFactory

}