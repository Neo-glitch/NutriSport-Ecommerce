package com.neo.di

import com.neo.admin_panel.AdminPanelViewModel
import com.neo.auth.AuthViewModel
import com.neo.cart.CartViewModel
import com.neo.category_search.CategorySearchViewModel
import com.neo.data.AdminRepositoryImpl
import com.neo.data.domain.CustomerRepository
import com.neo.data.CustomerRepositoryImpl
import com.neo.data.ProductRepositoryImpl
import com.neo.data.domain.AdminRepository
import com.neo.data.domain.ProductRepository
import com.neo.details.DetailsViewModel
import com.neo.home.HomeGraphViewModel
import com.neo.manage_product.ManageProductViewModel
import com.neo.products_overview.ProductsOverviewViewModel
import com.neo.profile.ProfileViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    single<AdminRepository> { AdminRepositoryImpl() }
    single<ProductRepository> { ProductRepositoryImpl() }
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::AdminPanelViewModel)
    viewModelOf(::ManageProductViewModel)
    viewModelOf(::ProductsOverviewViewModel)
    viewModelOf(::CategorySearchViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::AuthViewModel) // no need to specifically provide dependencies arg using this
}

// module to be implemented by platforms
expect val targetModule: Module

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, targetModule)
    }
}
