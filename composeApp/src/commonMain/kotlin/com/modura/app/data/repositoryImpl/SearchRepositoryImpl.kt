package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.DetailDataSource
import com.modura.app.data.datasource.SearchDataSource
import com.modura.app.domain.model.request.detail.DetailResponseModel
import com.modura.app.domain.model.response.search.SearchResponseModel
import com.modura.app.domain.model.response.youtube.YoutubeModel
import com.modura.app.domain.repository.DetailRepository
import com.modura.app.domain.repository.SearchRepository

class SearchRepositoryImpl (
    private val dataSource: SearchDataSource
): SearchRepository {
    override suspend fun searchContents(query: String): Result<List<List<SearchResponseModel>>>
        = runCatching { dataSource.searchContents(query).map { it.map { it.toSearchResponseModel() } } }
}