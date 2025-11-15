package com.modura.app.di

import androidx.lifecycle.get
import com.modura.app.ui.screens.login.LoginScreenModel
import org.koin.dsl.module

val appModule = module {
    includes(
        networkModule,
        repositoryModule,
        dataSourceModule,
        screenModelModule,
        storageModule,
        platformModule
    )
}