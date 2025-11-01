package com.modura.app.di

import com.modura.app.data.datasource.YoutubeDataSource
import com.modura.app.data.datasourceImpl.YoutubeDataSourceImpl
import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto
import com.modura.app.data.repositoryImpl.YoutubeRepositoryImpl
import com.modura.app.data.service.YoutubeService
import com.modura.app.domain.repository.YoutubeRepository
import com.modura.app.ui.screens.detail.DetailScreenModel
import com.modura.app.util.platform.getYoutubeApiKey
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient {
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
        }
    }

    single<YoutubeService> {
        object : YoutubeService {

            override suspend fun searchVideos(query: String): YoutubeSearchResponseDto {
                val client: HttpClient = get()
                val apiKey = getYoutubeApiKey()
                val baseUrl = "https://www.googleapis.com/youtube/v3/search"
                println("### 사용된 YouTube API 키: $apiKey")

                if (apiKey.isBlank() || apiKey.startsWith("여기에")) {
                    println("YouTube API 키가 설정되지 않았습니다.")
                    return YoutubeSearchResponseDto(emptyList())
                }

                return try {
                    client.get(baseUrl) {
                        parameter("key", apiKey)
                        parameter("part", "snippet")
                        parameter("q", query)
                        parameter("type", "video")
                        parameter("maxResults", 5)
                    }.body<YoutubeSearchResponseDto>()
                } catch (e: Exception) {
                    println("YouTube API 호출 중 오류 발생: ${e.message}")
                    YoutubeSearchResponseDto(emptyList())
                }
            }
        }
    }
}

val dataModule = module {
    single<YoutubeDataSource> { YoutubeDataSourceImpl(get()) }

    single<YoutubeRepository> { YoutubeRepositoryImpl(get()) }
}

// 3. ViewModel 모듈
val viewModelModule = module {
    factory { DetailScreenModel(get()) }
}
val koinModules = listOf(networkModule, dataModule, viewModelModule)
