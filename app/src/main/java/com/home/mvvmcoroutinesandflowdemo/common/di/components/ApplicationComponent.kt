package com.home.mvvmcoroutinesandflowdemo.common.di.components

import com.home.mvvmcoroutinesandflowdemo.common.di.modules.AppModule
import com.home.mvvmcoroutinesandflowdemo.common.di.modules.DbModule
import com.home.mvvmcoroutinesandflowdemo.common.di.modules.NetworkApiModule
import com.home.mvvmcoroutinesandflowdemo.common.di.modules.SubcomponentsModule
import com.home.mvvmcoroutinesandflowdemo.menu.di.MenuComponent
import com.squareup.moshi.Moshi
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetworkApiModule::class, DbModule::class, SubcomponentsModule::class])
@Singleton
interface ApplicationComponent {
    fun getMoshi(): Moshi
    fun homeComponent(): MenuComponent.Factory
}