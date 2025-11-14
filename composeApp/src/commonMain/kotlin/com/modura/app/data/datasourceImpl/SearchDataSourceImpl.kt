package com.modura.app.data.datasourceImpl

import com.modura.app.data.datasource.SearchDataSource
import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.search.SearchContentListResponseDto
import com.modura.app.data.dto.response.search.SearchPlaceListResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SearchDataSourceImpl(
    private val httpClient: HttpClient
) : SearchDataSource {
    override suspend fun searchContents(query: String): BaseResponse<SearchContentListResponseDto>
        = httpClient.get("search/contents?query=$query").body()

    override suspend fun searchPlaces(query: String): BaseResponse<SearchPlaceListResponseDto>
        = httpClient.get("search/places?query=$query").body()
}