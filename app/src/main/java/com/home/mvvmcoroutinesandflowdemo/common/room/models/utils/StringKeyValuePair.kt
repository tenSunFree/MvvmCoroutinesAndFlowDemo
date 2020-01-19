package com.home.mvvmcoroutinesandflowdemo.common.room.models.utils

import androidx.room.Entity

// primaryKeys: 組合主鍵
@Entity(primaryKeys = ["key"])
data class StringKeyValuePair(
    val key: String, val value: String
)