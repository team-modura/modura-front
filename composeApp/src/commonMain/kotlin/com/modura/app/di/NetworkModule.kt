package com.modura.app.di

import com.modura.app.data.dto.BaseResponse
import com.modura.app.domain.model.response.login.ReissueTokenResult
import com.modura.app.domain.repository.TokenRepository
import com.modura.app.util.platform.AI_BASE_URL
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin

object NetworkQualifiers {
    const val AUTH_HTTP_CLIENT = "AuthHttpClient"
    const val NO_AUTH_HTTP_CLIENT = "NoAuthHttpClient"
    const val TOKEN_REFRESH_HTTP_CLIENT = "TokenRefreshHttpClient"
    const val YOUTUBE_HTTP_CLIENT = "YoutubeHttpClient"
    const val AI_HTTP_CLIENT = "AiHttpClient"
}

val networkModule = module {
    single(named(NetworkQualifiers.AUTH_HTTP_CLIENT)) {
        val tokenRepository: TokenRepository = get()

        val tokenRefreshClient = HttpClient(CIO) {
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
            defaultRequest {
                url(BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }

        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) { level = LogLevel.ALL }

            defaultRequest {
                url(BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)

                val accessToken = runBlocking { tokenRepository.getAccessToken() }
                if (accessToken.isNotBlank()) {
                    header(HttpHeaders.Authorization, "Bearer $accessToken")
                }
            }

            install(HttpSend) {
                maxSendCount = 3
            }
        }.also { client ->
            client.plugin(HttpSend).intercept { request ->
                val originalCall = execute(request)

                if (originalCall.response.status.value == 401) {
                    val oldRefreshToken = tokenRepository.getRefreshToken()

                    if (oldRefreshToken.isNotBlank()) {
                        try {
                            val refreshResponse: BaseResponse<ReissueTokenResult> =
                                tokenRefreshClient.post("auth/reissue") {
                                    header("X-Refresh-Token", oldRefreshToken)
                                }.body()

                            val newTokens = refreshResponse.result
                            if (newTokens != null) {
                                tokenRepository.saveTokens(newTokens.accessToken, newTokens.refreshToken)
                                request.headers[HttpHeaders.Authorization] = "Bearer ${newTokens.accessToken}"
                                execute(request)
                            } else {
                                tokenRepository.clearTokens()
                                originalCall
                            }
                        } catch (e: Exception) {
                            tokenRepository.clearTokens()
                            originalCall
                        }
                    } else {
                        originalCall
                    }
                } else {
                    originalCall
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
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) { level = LogLevel.ALL }
            defaultRequest {
                url(BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
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
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
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
    single(named(NetworkQualifiers.AI_HTTP_CLIENT)) {
        val tokenRepository: TokenRepository = get()

        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) { level = LogLevel.ALL }

            install(HttpTimeout) {
                requestTimeoutMillis = 60000L
                connectTimeoutMillis = 60000L
                socketTimeoutMillis = 60000L
            }

            defaultRequest {
                url(AI_BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        runBlocking {
                            val accessToken = tokenRepository.getAccessToken()
                            val refreshToken = tokenRepository.getRefreshToken()
                            if (accessToken.isNotBlank()) {
                                BearerTokens(accessToken, refreshToken)
                            } else {
                                null
                            }
                        }
                    }
                }
            }

        }
    }
}