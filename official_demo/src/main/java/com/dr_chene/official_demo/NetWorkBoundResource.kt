package com.dr_chene.official_demo

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

/**
 * created by dr_chene on 2021/2/20
 * desc
 */
abstract class NetWorkBoundResource<T> {

    @WorkerThread
    protected abstract suspend fun saveCallResult(item: T)

    @MainThread
    protected abstract fun shouldFetch(data: T?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): Flow<T>

    @MainThread
    protected abstract fun createCall(): Flow<ApiResponse<T>>

    protected open fun onFetchFailed() {}
}