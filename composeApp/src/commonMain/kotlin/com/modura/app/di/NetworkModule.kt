package com.modura.app.di

import com.modura.app.domain.repository.TokenRepository
import com.modura.app.util.platform.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.http.encodedPath
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

object NetworkQualifiers {
    const val MODURA_HTTP_CLIENT = "ModuraHttpClient"
    const val YOUTUBE_HTTP_CLIENT = "YoutubeHttpClient"
}

val networkModule = module {
    single(named(NetworkQualifiers.MODURA_HTTP_CLIENT)) {
        val tokenRepository: TokenRepository = get()
        HttpClient {
            defaultRequest {
                println(BASE_URL)
                url(BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
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
                        println(">>> 401 Unauthorized 감지. 토큰 재발급 로직은 현재 주석 처리됨.")
                        tokenRepository.clearTokens()
                        null
                    }
                    /*sendWithoutRequest { request ->
                        request.url.encodedPath.endsWith("auth/login")
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
}