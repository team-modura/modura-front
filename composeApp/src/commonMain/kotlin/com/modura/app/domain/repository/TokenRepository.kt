package com.modura.app.domain.repository

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get

class TokenRepository(
    private val settings: Settings
) {
    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        settings.putString(ACCESS_TOKEN_KEY, accessToken)
        settings.putString(REFRESH_TOKEN_KEY, refreshToken)
        println("저장된 accessToken: ${settings.getString(ACCESS_TOKEN_KEY, "저장 실패")}")
        println("저장된 refreshToken: ${settings.getString(REFRESH_TOKEN_KEY, "저장 실패")}")
    }

    fun getAccessToken(): String {
        return settings.getString(ACCESS_TOKEN_KEY, "")
    }

    fun getRefreshToken(): String {
        return settings.getString(REFRESH_TOKEN_KEY, "")
    }

    fun clearTokens() {
        settings.remove(ACCESS_TOKEN_KEY)
        settings.remove(REFRESH_TOKEN_KEY)
    }
}