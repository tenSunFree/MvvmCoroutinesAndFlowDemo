package com.home.mvvmcoroutinesandflowdemo.menu.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.home.mvvmcoroutinesandflowdemo.common.di.ViewModelKey
import com.home.mvvmcoroutinesandflowdemo.common.di.ViewModelFactory
import com.home.mvvmcoroutinesandflowdemo.detail.viewmodel.DetailViewModel
import com.home.mvvmcoroutinesandflowdemo.menu.viewmodel.MenuViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MenuViewModels {

    @MenuScope
    @Binds
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    abstract fun bindWeatherViewModel(weatherViewModel: MenuViewModel): ViewModel

    @MenuScope
    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindForecastsViewModel(forecastsViewModel: DetailViewModel): ViewModel

    @MenuScope
    @Binds
    abstract fun bindHomeViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}