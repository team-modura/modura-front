package com.modura.app.data.datasourceImpl

import com.modura.app.data.datasource.ListDataSource
import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.list.MediaListResponseDto
import com.modura.app.data.dto.response.search.SearchContentListResponseDto
import com.modura.app.data.dto.response.search.SearchPlaceListResponseDto
import com.modura.app.data.service.ListService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ListDataSourceImpl(
    private val httpClient: HttpClient
) : ListDataSource {
    override suspend fun topPlaces(): BaseResponse<SearchPlaceListResponseDto> = httpClient.get("/search/top/places").body()
    override suspend fun topSeries(): BaseResponse<SearchContentListResponseDto> = httpClient.get("/search/top/series").body()
    override suspend fun topMovie(): BaseResponse<SearchContentListResponseDto> = httpClient.get("/search/top/movie").body()
}