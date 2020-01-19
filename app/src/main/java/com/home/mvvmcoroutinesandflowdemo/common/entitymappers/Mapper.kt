package com.home.mvvmcoroutinesandflowdemo.common.entitymappers

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface Mapper<T> {

    @WorkerThread
    suspend fun map(): T {
        // Dispatchers.Default 使用了共享的後台線程池
        return withContext(Dispatchers.Default) {
            getMapping()
        }
    }

    @WorkerThread
    fun getMapping(): T
}