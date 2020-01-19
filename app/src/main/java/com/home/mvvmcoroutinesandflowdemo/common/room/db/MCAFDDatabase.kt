package com.home.mvvmcoroutinesandflowdemo.common.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.home.mvvmcoroutinesandflowdemo.common.room.dao.DetailDao
import com.home.mvvmcoroutinesandflowdemo.common.room.dao.utils.StringKeyValueDao
import com.home.mvvmcoroutinesandflowdemo.common.room.models.DetailEntity
import com.home.mvvmcoroutinesandflowdemo.common.room.models.utils.StringKeyValuePair

@Database(entities = [DetailEntity::class, StringKeyValuePair::class], version = 1)
abstract class MCAFDDatabase : RoomDatabase() {
    abstract fun keyValueDao(): StringKeyValueDao
    abstract fun detailDao(): DetailDao
}