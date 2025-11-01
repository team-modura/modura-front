package com.modura.app.domain.repository

import com.modura.app.domain.model.response.youtube.YoutubeModel

interface YoutubeRepository {
    suspend fun getYoutubeVideos(query: String): Result<List<YoutubeModel>>
}