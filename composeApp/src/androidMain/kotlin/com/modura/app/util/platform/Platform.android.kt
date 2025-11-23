package com.modura.app.util.platform

import android.os.Build
import com.modura.app.BuildConfig

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
actual val BASE_URL: String = BuildConfig.BASE_URL
actual val AI_BASE_URL: String  = BuildConfig.AI_BASE_URL