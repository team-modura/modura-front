package com.modura.app.di

import androidx.lifecycle.get
import com.modura.app.ui.screens.login.LoginScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
    single<CoroutineScope> { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
}