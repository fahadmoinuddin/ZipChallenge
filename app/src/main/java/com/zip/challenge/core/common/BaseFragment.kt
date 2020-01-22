package com.zip.challenge.core.common

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

interface InitViews {
    fun initializeViews()
    fun observeViewModel()
}

abstract class BaseFragment : DaggerFragment(), InitViews {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(
            context,
            message,
            duration
        ).show()
    }

}