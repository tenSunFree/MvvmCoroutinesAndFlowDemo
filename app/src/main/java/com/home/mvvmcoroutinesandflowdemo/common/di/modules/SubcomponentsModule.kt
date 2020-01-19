package com.home.mvvmcoroutinesandflowdemo.common.di.modules

import com.home.mvvmcoroutinesandflowdemo.menu.di.MenuComponent
import dagger.Module

@Module(subcomponents = [MenuComponent::class])
class SubcomponentsModule