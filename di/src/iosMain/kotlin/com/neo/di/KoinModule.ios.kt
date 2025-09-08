package com.neo.di

import com.neo.manage_product.PhotoPicker
import org.koin.dsl.module

actual val targetModule = module {
    single<PhotoPicker> { PhotoPicker() }
}