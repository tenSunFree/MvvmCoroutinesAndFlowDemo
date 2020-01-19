package com.home.mvvmcoroutinesandflowdemo.detail.model

import com.home.mvvmcoroutinesandflowdemo.common.base.Error
import com.home.mvvmcoroutinesandflowdemo.common.base.Success
import com.home.mvvmcoroutinesandflowdemo.common.custom.errors.ErrorHandler
import com.home.mvvmcoroutinesandflowdemo.common.custom.errors.NoDataException
import com.home.mvvmcoroutinesandflowdemo.common.custom.errors.NoResponseException
import com.home.mvvmcoroutinesandflowdemo.common.entitymappers.DetailMapper
import com.home.mvvmcoroutinesandflowdemo.common.extensions.applyCommonSideEffects
import com.home.mvvmcoroutinesandflowdemo.common.network.api.MenuApi
import com.home.mvvmcoroutinesandflowdemo.common.network.response.ErrorResponse
import com.home.mvvmcoroutinesandflowdemo.common.room.dao.DetailDao
import com.home.mvvmcoroutinesandflowdemo.common.room.dao.utils.StringKeyValueDao
import com.home.mvvmcoroutinesandflowdemo.common.utils.Utils
import com.home.mvvmcoroutinesandflowdemo.menu.di.MenuScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@MenuScope
class DetailRepository @Inject constructor(
    private val menuApi: MenuApi,
    private val detailDao: DetailDao,
    private val stringKeyValueDao: StringKeyValueDao
) {

    private val forecastCacheThresholdMillis = 60000L

    @ExperimentalCoroutinesApi
    fun getForecasts() = flow {
        stringKeyValueDao.get(Utils.LAST_FORECASTS_API_CALL_TIMESTAMP)
            ?.takeIf { !Utils.shouldCallApi(it.value, forecastCacheThresholdMillis) }
            ?.let { emit(getDataOrError(NoDataException())) }
            ?: emit((getForecastFromAPI()))
    }
        .applyCommonSideEffects()
        .catch { emit(getDataOrError(it)) }

    private suspend fun getForecastFromAPI() = menuApi.getWeatherForecast2()
        .run {
            if (isSuccessful && body() != null) {
                stringKeyValueDao.insert(
                    Utils.getCurrentTimeKeyValuePair(Utils.LAST_FORECASTS_API_CALL_TIMESTAMP)
                )
                detailDao.deleteDetailAndInsert(DetailMapper(body()!!).map())
                getDataOrError(NoDataException())
            } else {
                Error(
                    NoResponseException(
                        ErrorHandler.parseError<ErrorResponse>(errorBody())?.message
                    )
                )
            }
        }

    private suspend fun getDataOrError(throwable: Throwable) =
        detailDao.getDetailList()
            ?.let { dbValue -> Success(dbValue) }
            ?: Error(throwable)
}
