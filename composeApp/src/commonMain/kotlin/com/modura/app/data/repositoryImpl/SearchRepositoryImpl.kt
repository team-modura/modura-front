package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.SearchDataSource
import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.search.SearchListResponseDto
import com.modura.app.domain.model.response.search.SearchListResponseModel
import com.modura.app.domain.model.response.search.SearchResponseModel
import com.modura.app.domain.repository.SearchRepository

class SearchRepositoryImpl (
    private val dataSource: SearchDataSource
): SearchRepository {
    override suspend fun searchContents(query: String): Result<SearchListResponseModel> =
         runCatching {
            val response = dataSource.searchContents(query)
            if (response.isSuccess && response.result != null) {
                response.result.toSearchListResponseModel()
            } else {
                throw Exception(response.message)
            }
    }

    override suspend fun searchPlaces(query: String): Result<SearchListResponseModel> =
        runCatching {
            val response = dataSource.searchPlaces(query)
            if (response.isSuccess && response.result != null) {
                response.result.toSearchListResponseModel()
            } else {
                throw Exception(response.message)
            }
        }
}