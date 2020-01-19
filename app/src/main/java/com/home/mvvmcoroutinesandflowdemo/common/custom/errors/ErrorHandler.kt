package com.home.mvvmcoroutinesandflowdemo.common.custom.errors

import android.content.Context
import android.view.View
import android.widget.Toast
import com.home.mvvmcoroutinesandflowdemo.common.MCAFDApplication
import com.home.mvvmcoroutinesandflowdemo.common.utils.Loggerg
import com.home.mvvmcoroutinesandflowdemo.common.base.Error
import com.home.mvvmcoroutinesandflowdemo.common.custom.views.IndefiniteSnackbar
import com.squareup.moshi.JsonDataException
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException

object ErrorHandler {

    private const val NETWORK_ERROR_MESSAGE =
        "Please check your internet connectivity and try again!"
    private const val EMPTY_RESPONSE = "Server returned empty response."
    const val NO_SUCH_DATA = "Data not found in the database"
    const val UNKNOWN_ERROR = "An unknown error occurred!"

    fun handleError(
        view: View,
        throwable: Error,
        shouldToast: Boolean = false,
        shouldShowSnackBar: Boolean = false,
        refreshAction: () -> Unit = {}
    ) {
        if (shouldShowSnackBar) {
            showSnackBar(view, message = throwable.message, refresh = refreshAction)
        } else {
            if (shouldToast) {
                showLongToast(view.context, throwable.message)
            }
        }
        when (throwable.exception) {
            is IOException -> Loggerg.d(NETWORK_ERROR_MESSAGE)
            is HttpException -> Loggerg.d(
                "HTTP Exception: ${throwable.exception.code()}"
            )
            is NoResponseException -> Loggerg.d(EMPTY_RESPONSE)
            is NoDataException -> Loggerg.d(NO_SUCH_DATA)
            else -> Loggerg.d(throwable.message)
        }
    }

    private fun showSnackBar(view: View, message: String, refresh: () -> Unit = {}) {
        IndefiniteSnackbar.show(view, message, refresh)
    }

    private fun showLongToast(context: Context, message: String) = Toast.makeText(
        context, message, Toast.LENGTH_LONG
    ).show()

    inline fun <reified T> parseError(responseBody: ResponseBody?): T? {
        val parser = MCAFDApplication.moshi.adapter(T::class.java)
        val response = responseBody?.string()
        if (response != null)
            try {
                return parser.fromJson(response)
            } catch (e: JsonDataException) {
                e.printStackTrace()
            }
        return null
    }

}