package com.home.mvvmcoroutinesandflowdemo.common.di.modules

import android.content.Context
import androidx.room.Room
import com.home.mvvmcoroutinesandflowdemo.common.room.db.MCAFDDatabase
import com.home.mvvmcoroutinesandflowdemo.common.utils.Utils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideWeatherDB(context: Context): MCAFDDatabase {
        return Room.databaseBuilder(context, MCAFDDatabase::class.java, Utils.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideDetailDao(weatherDatabase: MCAFDDatabase) = weatherDatabase.detailDao()

    @Provides
    @Singleton
    fun provideStringKeyValueDao(weatherDatabase: MCAFDDatabase) = weatherDatabase.keyValueDao()
}