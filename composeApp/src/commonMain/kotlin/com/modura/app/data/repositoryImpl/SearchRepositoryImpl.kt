package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.SearchDataSource
import com.modura.app.domain.model.response.search.SearchContentListResponseModel
import com.modura.app.domain.model.response.search.SearchPlaceListResponseModel
import com.modura.app.domain.repository.SearchRepository

class SearchRepositoryImpl (
    private val dataSource: SearchDataSource
): SearchRepository {
    override suspend fun searchContents(query: String): Result<SearchContentListResponseModel> =
         runCatching {
            val response = dataSource.searchContents(query)
            if (response.isSuccess && response.result != null) {
                response.result.toSearchContentListResponseModel()
            } else {
                throw Exception(response.message)
            }
    }

    override suspend fun searchPlaces(query: String): Result<SearchPlaceListResponseModel> =
        runCatching {
            val response = dataSource.searchPlaces(query)
            if (response.isSuccess && response.result != null) {
                response.result.toSearchPlaceListResponseModel()
            } else {
                throw Exception(response.message)
            }
        }
}