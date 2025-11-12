package com.modura.app.util.network

import androidx.compose.runtime.Composable

@Composable
expect fun rememberKakaoAuth(
    onResult: (code: String?, error: String?) -> Unit
): KakaoAuth

interface KakaoAuth {
    fun login()
    fun logout()
}