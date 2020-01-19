package com.home.mvvmcoroutinesandflowdemo.common.extensions

import com.home.mvvmcoroutinesandflowdemo.common.base.Progress
import com.home.mvvmcoroutinesandflowdemo.common.base.Result
import com.home.mvvmcoroutinesandflowdemo.common.utils.Utils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import okhttp3.Call
import okhttp3.Request
import retrofit2.Retrofit
import java.io.IOException

@PublishedApi
internal inline fun Retrofit.Builder.callFactory(crossinline body: (Request) -> Call) =
    callFactory(object : Call.Factory {
        override fun newCall(request: Request): Call = body(request)
    })

/**
 * You may want to apply some common side-effects to your flow to avoid repeating commonly used
 * logic across your app.
 *
 * For e.g. If you want to show/hide progress then use side-effect methods like
 * onStart & onCompletion
 *
 * You can also write common business logic which is applicable to all flows in your application,
 * in this case we are retrying requests 3 times with an exponential delay; if the exception thrown
 * is of type IOException.
 *
 */
@ExperimentalCoroutinesApi
fun <T : Any> Flow<Result<T>>.applyCommonSideEffects() =
    retryWhen { cause, attempt ->
        // 如果返回true 則再次執行getWeather(); 否則執行catch{}
        when {
            (cause is IOException && attempt < Utils.MAX_RETRIES) -> {
                delay(Utils.getBackoffDelay(attempt))
                false
            }
            else -> false
        }
    }
        .onStart { emit(Progress(isLoading = true)) }
        .onCompletion { emit(Progress(isLoading = false)) }

fun Job?.cancelIfActive() {
    if (this?.isActive == true) {
        cancel()
    }
}
