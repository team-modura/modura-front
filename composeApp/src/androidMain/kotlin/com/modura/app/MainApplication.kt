package com.modura.app

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import com.kakao.vectormap.KakaoMapSdk
import com.modura.app.di.koinModules
import org.koin.core.context.startKoin
import java.security.MessageDigest

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        startKoin {
            modules(koinModules)
        }

        try {
            val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES).signingInfo?.apkContentsSigners
            } else {
                @Suppress("DEPRECATION")
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures
            }
            signatures?.forEach { signature ->
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val keyHash = Base64.encodeToString(md.digest(), Base64.NO_WRAP)
                Log.d("KeyHash", "내 애플리케이션의 키 해시는: $keyHash")
            }
        } catch (e: Exception) {
            Log.e("KeyHash", "키 해시를 가져오는 데 실패했습니다.", e)
        }

        try {
            KakaoMapSdk.init(this, getString(R.string.kakao_native_app_key))
            Log.d("KakaoMap", "Kakao SDK Initialized SUCCESSFULLY")
        } catch (e: Exception) {
            Log.e("KakaoMap", "Kakao SDK Initialization FAILED", e)
        }
    }

    companion object {
        lateinit var INSTANCE: MainApplication
            private set
    }
}