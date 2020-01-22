package com.zip.shared.util

import android.util.Log
import com.zip.shared.BuildConfig

/**
 * Custom logger class to log information
 */
object Logger {

    private const val tag = "Zip Challenege"
    private val enableLogging = BuildConfig.ENABLE_LOGGING

    fun logError(msg: String) {
        if (enableLogging) {
            Log.e(tag, msg)
        }
    }

    fun logError(e: Exception) {
        if (enableLogging) {
            Log.e(tag, Log.getStackTraceString(e))
        }
    }

    fun logInfo(msg: String) {
        if (enableLogging) {
            Log.i(tag, msg)
        }
    }

    fun logWarn(msg: String) {
        if (enableLogging) {
            Log.w(tag, msg)
        }
    }

}