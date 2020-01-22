package com.zip.challenge.companyProfile

import androidx.lifecycle.ViewModel
import com.zip.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Module which is used to inject [CompanyProfileViewModel] through dependency injection
 */
@Module
abstract class CompanyProfileModule {

    @Binds
    @IntoMap
    @ViewModelKey(CompanyProfileViewModel::class)
    abstract fun bindCompanyProfileViewModel(viewModel: CompanyProfileViewModel): ViewModel
}