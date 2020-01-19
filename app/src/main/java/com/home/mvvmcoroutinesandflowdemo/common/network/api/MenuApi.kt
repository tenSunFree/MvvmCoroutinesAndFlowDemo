package com.home.mvvmcoroutinesandflowdemo.common.network.api

import androidx.annotation.WorkerThread
import com.home.mvvmcoroutinesandflowdemo.common.network.response.NasaResponse
import com.home.mvvmcoroutinesandflowdemo.common.utils.Utils
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MenuApi {

    @WorkerThread
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getWeatherForecast2(
        @Query("sol") sol: Int = 1000,
        @Query("api_key") apiKey: String = Utils.OPEN_API_KEY
    ): Response<NasaResponse>
}