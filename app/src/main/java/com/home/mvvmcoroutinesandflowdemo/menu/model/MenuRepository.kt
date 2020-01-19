package com.home.mvvmcoroutinesandflowdemo.menu.model

import com.home.mvvmcoroutinesandflowdemo.menu.di.MenuScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@MenuScope
class MenuRepository @Inject constructor(
    private val menuModel: MenuModel
) {

    fun getMenuName(): Flow<String> {
        return menuModel.getMenuName()
    }
}