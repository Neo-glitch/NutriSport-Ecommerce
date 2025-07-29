package com.neo.di

import com.neo.auth.AuthViewModel
import com.neo.data.domain.CustomerRepository
import com.neo.data.domain.CustomerRepositoryImpl
import com.neo.home.HomeGraphViewModel
import com.neo.profile.ProfileViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::AuthViewModel) // no need to specifically provide dependencies arg using this
}

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule)
    }
}
