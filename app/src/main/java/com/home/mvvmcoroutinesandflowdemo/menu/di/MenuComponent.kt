package com.home.mvvmcoroutinesandflowdemo.menu.di

import com.home.mvvmcoroutinesandflowdemo.detail.view.DetailFragment
import com.home.mvvmcoroutinesandflowdemo.menu.view.MenuActivity
import com.home.mvvmcoroutinesandflowdemo.menu.view.MenuFragment
import dagger.Subcomponent

@MenuScope
@Subcomponent(modules = [MenuViewModels::class])
interface MenuComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MenuComponent
    }

    fun inject(homeActivity: MenuActivity)
    fun inject(weatherFragment: MenuFragment)
    fun inject(forecastsFragment: DetailFragment)
}