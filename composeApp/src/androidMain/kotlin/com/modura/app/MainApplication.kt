package com.modura.app

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.vectormap.KakaoMapSdk
import com.modura.app.di.*
import com.modura.app.util.platform.appContext
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.security.MessageDigest

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appContext = this
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
        KakaoMapSdk.init(this, getString(R.string.kakao_native_app_key))
    }

    companion object {
        lateinit var INSTANCE: MainApplication
            private set
    }
}