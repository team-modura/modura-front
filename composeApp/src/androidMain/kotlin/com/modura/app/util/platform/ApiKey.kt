package com.modura.app.util.platform

import com.modura.app.BuildConfig

actual fun getYoutubeApiKey(): String {
    return BuildConfig.YOUTUBE_API_KEY
}