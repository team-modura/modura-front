package com.modura.app

import android.app.Application

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: MainApplication
            private set
    }
}