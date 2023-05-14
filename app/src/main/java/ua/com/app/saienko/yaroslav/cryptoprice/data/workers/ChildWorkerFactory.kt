package ua.com.app.saienko.yaroslav.cryptoprice.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface ChildWorkerFactory {

    fun create(
        context: Context,
        workerParams: WorkerParameters,
    ) : ListenableWorker

}