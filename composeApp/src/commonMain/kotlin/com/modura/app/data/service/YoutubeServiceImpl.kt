package com.modura.app.data.service

import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto
import com.modura.app.util.platform.getYoutubeApiKey
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class YoutubeServiceImpl(private val client: HttpClient) : YoutubeService {
    private val youtubeUrl = "search"

    override suspend fun searchVideos(query: String): YoutubeSearchResponseDto {
        val apiKey = getYoutubeApiKey()
        println("### 사용된 YouTube API 키: $apiKey")

        if (apiKey.isBlank() || apiKey.startsWith("여기에")) {
            println("YouTube API 키가 설정되지 않았습니다.")
            return YoutubeSearchResponseDto(emptyList())
        }

        return try {
            client.get(youtubeUrl) {
                parameter("key", apiKey)
                parameter("part", "snippet")
                parameter("q", query)
                parameter("type", "video")
                parameter("maxResults", 5)
            }.body()
        } catch (e: Exception) {
            println("YouTube API 호출 중 오류 발생: ${e.message}")
            YoutubeSearchResponseDto(emptyList())
        }
    }
}