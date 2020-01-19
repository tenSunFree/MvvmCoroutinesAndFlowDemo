package com.home.mvvmcoroutinesandflowdemo.menu.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.home.mvvmcoroutinesandflowdemo.menu.di.MenuScope
import com.home.mvvmcoroutinesandflowdemo.menu.model.MenuRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

@MenuScope
class MenuViewModel @Inject constructor(private val weatherRepository: MenuRepository) :
    ViewModel() {

    private val mutableLiveData = MutableLiveData<List<String>>()
    val openMutableLiveData = mutableLiveData

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun getWeather() {
        weatherRepository.getMenuName().let {
            CoroutineScope(Dispatchers.Main).launch {
                val output = it.flatMapMerge {
                    flow { emit(it) }
                }.toList()
                mutableLiveData.value = output
            }
        }
    }
}
