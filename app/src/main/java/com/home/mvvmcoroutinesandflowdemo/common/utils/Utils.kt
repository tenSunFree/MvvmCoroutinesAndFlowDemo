package com.home.mvvmcoroutinesandflowdemo.common.utils

import com.home.mvvmcoroutinesandflowdemo.common.room.models.utils.StringKeyValuePair

object Utils {

    const val OPEN_API_KEY = "4csHs6VKhYet19ItxFE5fM4cMMVPqRheFQAHzGpZ"
    const val BASE_URL = "https://api.nasa.gov/"
    const val DATABASE_NAME = "MvvmCoroutinesAndFlowDemo"
    const val LAST_FORECASTS_API_CALL_TIMESTAMP = "last_api_call_timestamp"
    const val MAX_RETRIES = 3L
    private const val INITIAL_BACKOFF = 1000L

    fun shouldCallApi(
        lastApiCallMillis: String,
        cacheThresholdInMillis: Long = 300000L // default value is 5 minutes
    ): Boolean {
        return (System.currentTimeMillis() - lastApiCallMillis.toLong()) >= cacheThresholdInMillis
    }

    fun getCurrentTimeKeyValuePair(key: String): StringKeyValuePair {
        return StringKeyValuePair(key, System.currentTimeMillis().toString())
    }

    fun getBackoffDelay(attempt: Long) = INITIAL_BACKOFF * (attempt + 1)
}