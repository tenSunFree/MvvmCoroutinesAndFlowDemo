package com.home.mvvmcoroutinesandflowdemo.common

import android.app.Application
import com.facebook.stetho.Stetho
import com.home.mvvmcoroutinesandflowdemo.common.di.components.ApplicationComponent
import com.home.mvvmcoroutinesandflowdemo.common.di.components.DaggerApplicationComponent
import com.home.mvvmcoroutinesandflowdemo.common.di.modules.AppModule
import com.home.mvvmcoroutinesandflowdemo.common.di.modules.DbModule
import com.home.mvvmcoroutinesandflowdemo.common.di.modules.NetworkApiModule
import com.squareup.moshi.Moshi

class MCAFDApplication : Application() {

    lateinit var appComponent: ApplicationComponent

    companion object {
        lateinit var moshi: Moshi
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        initDaggerComponent()
        moshi = appComponent.getMoshi()
    }

    private fun initDaggerComponent() {
        appComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(applicationContext))
            .dbModule(DbModule())
            .networkApiModule(NetworkApiModule())
            .build()
    }
}