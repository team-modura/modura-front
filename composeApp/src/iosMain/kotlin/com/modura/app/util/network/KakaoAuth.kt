package com.modura.app.util.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


//Swift로 열면 주석 제거
@Composable
actual fun rememberKakaoAuth(
    onResult: (token: String?, error: String?) -> Unit
): KakaoAuth {
   return remember {
      /*  object : KakaoAuth {
            override fun login() {
                val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if (error != null) {
                        // 로그인 실패 처리
                        onResult(null, error.message)
                    } else if (token != null) {
                        // 로그인 성공 처리
                        onResult(token.accessToken, null)
                    }
                }

                if (UserApiClient.instance.isKakaoTalkLoginAvailable()) {
                    UserApiClient.instance.loginWithKakaoTalk(callback = callback)
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(callback = callback)
                }
            }

            override fun logout() {
                UserApiClient.instance.logout { error ->
                    // 로그아웃 처리
                }
            }
        }*/
    }
}