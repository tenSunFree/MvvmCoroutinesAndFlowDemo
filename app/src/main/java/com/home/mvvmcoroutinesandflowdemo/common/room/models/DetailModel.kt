package com.home.mvvmcoroutinesandflowdemo.common.room.models

import androidx.room.Entity

data class DetailModel(
    val list: List<DetailEntity>
)

@Entity(primaryKeys = ["id"])
data class DetailEntity(
    val id: Int,
    val img_src: String
)

