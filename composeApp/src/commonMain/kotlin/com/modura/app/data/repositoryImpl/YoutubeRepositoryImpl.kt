package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.YoutubeDataSource
import com.modura.app.domain.model.response.youtube.YoutubeModel
import com.modura.app.domain.repository.YoutubeRepository

class YoutubeRepositoryImpl (
    private val youtubeDataStore: YoutubeDataSource
): YoutubeRepository{
    override suspend fun getYoutubeVideos(query: String): Result<List<YoutubeModel>> =
        runCatching { youtubeDataStore.searchYoutubeVideos(query).items.map { it.toYoutubeModel() } }
}