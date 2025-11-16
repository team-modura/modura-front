package com.modura.app.domain.repository

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext

class TokenRepository(
    private val settings: Settings
) {
    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        settings.putString(ACCESS_TOKEN_KEY, accessToken)
        settings.putString(REFRESH_TOKEN_KEY, refreshToken)
        println(">>> 토큰 저장 요청 완료 (Save Requested) <<<")
    }

    suspend fun  getAccessToken(): String {
        return settings.getString(ACCESS_TOKEN_KEY, "")
    }

    suspend fun  getRefreshToken(): String {
        return settings.getString(REFRESH_TOKEN_KEY, "")
    }

    suspend fun  clearTokens() {
        settings.remove(ACCESS_TOKEN_KEY)
        settings.remove(REFRESH_TOKEN_KEY)
    }
}