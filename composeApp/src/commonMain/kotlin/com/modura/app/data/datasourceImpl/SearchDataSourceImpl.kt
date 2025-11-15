package com.modura.app.data.datasourceImpl

import com.modura.app.data.datasource.SearchDataSource
import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.search.SearchContentListResponseDto
import com.modura.app.data.dto.response.search.SearchPlaceListResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodeURLParameter

class SearchDataSourceImpl(
    private val httpClient: HttpClient
) : SearchDataSource {
    override suspend fun searchContents(query: String): BaseResponse<SearchContentListResponseDto> {
        val encodedQuery = query.encodeURLParameter()
        return httpClient.get("search/contents?query=$encodedQuery").body()
    }

    override suspend fun searchPlaces(query: String): BaseResponse<SearchPlaceListResponseDto> {
        val encodedQuery = query.encodeURLParameter()
        return httpClient.get("search/places?query=$encodedQuery").body()
    }
}