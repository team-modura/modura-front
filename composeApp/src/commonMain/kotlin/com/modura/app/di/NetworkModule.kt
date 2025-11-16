package com.modura.app.di

import com.modura.app.data.dto.BaseResponse
import com.modura.app.domain.model.response.login.ReissueTokenResult
import com.modura.app.domain.repository.TokenRepository
import com.modura.app.util.platform.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.post
import io.ktor.http.encodedPath
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

object NetworkQualifiers {
    const val MODURA_HTTP_CLIENT = "ModuraHttpClient"
    const val YOUTUBE_HTTP_CLIENT = "YoutubeHttpClient"
    const val NO_AUTH_HTTP_CLIENT = "NoAuthHttpClient"
    const val TOKEN_REFRESH_HTTP_CLIENT = "TokenRefresHttpClient"
}

val networkModule = module {
    single(named(NetworkQualifiers.MODURA_HTTP_CLIENT)) {
        val noAuthHttpClient: HttpClient = get(named(NetworkQualifiers.NO_AUTH_HTTP_CLIENT))
        val tokenRepository: TokenRepository = get()
        val tokenRefreshHttpClient: HttpClient = get(named(NetworkQualifiers.TOKEN_REFRESH_HTTP_CLIENT))
        HttpClient(CIO) {
            defaultRequest {
                url(BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L // 요청 전체에 대한 타임아웃 (15초)
                connectTimeoutMillis = 15000L // 서버 연결에 대한 타임아웃 (15초)
                socketTimeoutMillis = 15000L  // 데이터 수신에 대한 타임아웃 (15초)
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = tokenRepository.getAccessToken()
                        val refreshToken = tokenRepository.getRefreshToken()

                        if (accessToken.isNotBlank() && refreshToken.isNotBlank()) {
                            BearerTokens(accessToken, refreshToken)
                        } else {
                            null
                        }
                    }
                    refreshTokens {
                        println(">>> 401 Unauthorized 감지. 토큰 재발급을 시도합니다.")

                        val oldRefreshToken = tokenRepository.getRefreshToken()
                        if (oldRefreshToken.isBlank()) {
                            println(">>> 저장된 Refresh Token이 없어 재발급을 중단합니다.")
                            tokenRepository.clearTokens()
                            return@refreshTokens null
                        }
                        val response: BaseResponse<ReissueTokenResult> =
                            tokenRefreshHttpClient.post("auth/reissue") { // BASE_URL이 이미 설정되어 있으므로 경로만 적습니다.
                                header("X-Refresh-Token", oldRefreshToken)
                            }.body()

                        val newTokens = response.result

                        tokenRepository.saveTokens(newTokens?.accessToken ?: "", newTokens?.refreshToken ?: "")
                        println(">>> 토큰 재발급 및 저장 성공!")

                        BearerTokens(newTokens?.accessToken ?: "", newTokens?.refreshToken)
                    }
                    sendWithoutRequest { request ->
                        request.url.host == "flicker-bucket.s3.ap-northeast-2.amazonaws.com"
                    }
                    /*sendWithoutRequest { request ->
                        val urlPath = request.url.encodedPath
                        val shouldSendWithoutToken = urlPath.startsWith("/s3/")

                        println("--- Auth Plugin Check ---")
                        println("Request URL Path: $urlPath")
                        println("Condition (startsWith /s3/): $shouldSendWithoutToken")

                        shouldSendWithoutToken
                    }*/
                }
            }
        }
    }
    single(named(NetworkQualifiers.YOUTUBE_HTTP_CLIENT)) {
        HttpClient {
            defaultRequest {
                url("https://www.googleapis.com/youtube/v3/")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(ContentNegotiation) { json(Json { prettyPrint = true;isLenient = true;ignoreUnknownKeys = true }) }
            install(Logging) { level = LogLevel.ALL }
        }
    }
    single(named(NetworkQualifiers.NO_AUTH_HTTP_CLIENT)) {
        val tokenRepository: TokenRepository = get()
        HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger { override fun log(message: String) { println("S3 Uploader Log: $message") } }
            }
        }
    }
    single(named(NetworkQualifiers.TOKEN_REFRESH_HTTP_CLIENT)) {
        HttpClient(CIO) {
            defaultRequest {
                url(BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L // 요청 전체에 대한 타임아웃 (15초)
                connectTimeoutMillis = 15000L // 서버 연결에 대한 타임아웃 (15초)
                socketTimeoutMillis = 15000L  // 데이터 수신에 대한 타임아웃 (15초)
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger { override fun log(message: String) { println("Token Refresher Log: $message") } }
            }
        }
    }
}