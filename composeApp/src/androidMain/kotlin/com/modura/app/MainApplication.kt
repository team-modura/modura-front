package com.modura.app

import android.app.Application
import com.modura.app.di.koinModules
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        startKoin {
            modules(koinModules)
        }
    }

    companion object {
        lateinit var INSTANCE: MainApplication
            private set
    }
}