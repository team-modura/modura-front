package com.modura.app.di

import com.modura.app.util.location.LocationHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single { LocationHelper(androidContext()) }
}