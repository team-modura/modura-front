package com.modura.app.domain.repository

import com.modura.app.domain.model.response.detail.DetailResponseModel
import com.modura.app.domain.model.response.youtube.YoutubeModel

interface DetailRepository {
    suspend fun getYoutubeVideos(query: String): Result<List<YoutubeModel>>
    suspend fun detailContent(contentId: Int): Result<DetailResponseModel>
}