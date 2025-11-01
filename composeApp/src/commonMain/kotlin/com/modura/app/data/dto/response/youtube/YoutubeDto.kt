package com.modura.app.data.dto.response.youtube

import com.modura.app.domain.model.response.youtube.YoutubeModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//유튜브 검색 API의 최상위 JSON 응답 구조를 담는 DTO
@Serializable
data class YoutubeSearchResponseDto(
    @SerialName("items") val items: List<YoutubeItemDto>
)

//개별 비디오 아이템 DTO
@Serializable
data class YoutubeItemDto(
    @SerialName("id") val id: VideoIdDto,
    @SerialName("snippet") val snippet: SnippetDto
){
    fun toYoutubeModel(): YoutubeModel {
        return YoutubeModel(
            videoId = this.id.videoId,
            title = this.snippet.title,
            thumbnailUrl = this.snippet.thumbnails.high.url
        )
    }
}

// --- 아래는 보조 DTO들 ---
@Serializable
data class VideoIdDto(
    @SerialName("videoId") val videoId: String
)

@Serializable
data class SnippetDto(
    @SerialName("title") val title: String,
    @SerialName("thumbnails") val thumbnails: ThumbnailsDto
)

@Serializable
data class ThumbnailsDto(
    @SerialName("high") val high: ThumbnailInfoDto
)

@Serializable
data class ThumbnailInfoDto(
    @SerialName("url") val url: String
)
