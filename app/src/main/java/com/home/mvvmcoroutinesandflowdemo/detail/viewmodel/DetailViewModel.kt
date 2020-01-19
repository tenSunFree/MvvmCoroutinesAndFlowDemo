package com.home.mvvmcoroutinesandflowdemo.detail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.home.mvvmcoroutinesandflowdemo.common.custom.`typealias`.DetailEntityListResults
import com.home.mvvmcoroutinesandflowdemo.common.extensions.cancelIfActive
import com.home.mvvmcoroutinesandflowdemo.detail.model.DetailRepository
import com.home.mvvmcoroutinesandflowdemo.menu.di.MenuScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@MenuScope
class DetailViewModel @Inject constructor(
    private val forecastsRepository: DetailRepository
) : ViewModel() {

    private val mutableForecastLiveData = MutableLiveData<DetailEntityListResults>()
    private var getForecastsJob: Job? = null

    val forecastLiveData = mutableForecastLiveData

    /**
     * Cancel existing job if running and then launch forecastsRepository.getForecasts using
     * viewModelScope
     */
    @ExperimentalCoroutinesApi
    fun getForecasts() {
        getForecastsJob = GlobalScope.launch(Dispatchers.Main) {
            forecastsRepository.getForecasts()
                .collect { mutableForecastLiveData.value = it }
        }
    }

    fun cancelIfActive() {
        getForecastsJob.cancelIfActive()
    }

    override fun onCleared() {
        cancelIfActive()
        super.onCleared()
    }
}