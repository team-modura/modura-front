package com.modura.app.di

import com.russhwolf.settings.Settings
import com.modura.app.domain.repository.TokenRepository
import org.koin.dsl.module

val storageModule = module {
    factory { Settings() }
    single { TokenRepository(get()) }
}