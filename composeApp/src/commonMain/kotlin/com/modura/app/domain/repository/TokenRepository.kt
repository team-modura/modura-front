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
        private const val USER_ID_KEY = "user_id"
        private const val USERNAME_KEY = "username"
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
        settings.remove(USER_ID_KEY)
        settings.remove(USERNAME_KEY)
    }

    suspend fun saveUserInfo(id: Int, username: String) {
        settings.putInt(USER_ID_KEY, id)
        settings.putString(USERNAME_KEY, username)
    }

    suspend fun getUserId(): Int = settings.getInt(USER_ID_KEY, -1)
    suspend fun getUsername(): String = settings.getString(USERNAME_KEY, "")
}