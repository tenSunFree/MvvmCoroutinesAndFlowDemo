package com.home.mvvmcoroutinesandflowdemo.common.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppContext() = context
}