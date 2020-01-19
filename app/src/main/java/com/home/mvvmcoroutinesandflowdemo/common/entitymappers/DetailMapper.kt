package com.home.mvvmcoroutinesandflowdemo.common.entitymappers

import androidx.annotation.WorkerThread
import com.home.mvvmcoroutinesandflowdemo.common.network.response.NasaResponse
import com.home.mvvmcoroutinesandflowdemo.common.room.models.DetailEntity
import com.home.mvvmcoroutinesandflowdemo.common.room.models.DetailModel

class DetailMapper(private val apiForecast: NasaResponse) : Mapper<DetailModel> {

    @WorkerThread
    override fun getMapping(): DetailModel {
        return DetailModel(
            apiForecast.photos.asSequence().map { DetailEntity(it.id, it.img_src) }.toList()
        )
    }
}