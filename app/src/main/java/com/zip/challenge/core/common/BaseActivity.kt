package com.zip.challenge.core.common

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * [BaseActivity] contains common methods and properties and all activities must extend this class
 */
abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(
            this,
            message,
            duration
        ).show()
    }
}