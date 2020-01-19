package com.home.mvvmcoroutinesandflowdemo.menu.model

import com.home.mvvmcoroutinesandflowdemo.menu.di.MenuScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@MenuScope
class MenuModel @Inject constructor() {

    fun getMenuName(): Flow<String> {
        return flowOf(
            "Mars Rover Photos", "APOD", "Asteroids NeoWs", "DONKI", "Earth", "EONET", "EPIC"
            , "Exoplanet Archive", "GeneLab", "InSight"
        )
    }
}