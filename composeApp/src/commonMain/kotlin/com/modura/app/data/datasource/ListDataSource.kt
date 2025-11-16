package com.modura.app.data.datasource

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.list.MediaListResponseDto
import com.modura.app.data.dto.response.map.PlacesResponseDto
import com.modura.app.data.dto.response.search.SearchContentListResponseDto
import com.modura.app.data.dto.response.search.SearchPlaceListResponseDto
import com.modura.app.data.dto.response.search.SearchPlaceResponseDto

interface ListDataSource {
    suspend fun topPlaces(): BaseResponse<SearchPlaceListResponseDto>
    suspend fun topSeries(): BaseResponse<SearchContentListResponseDto>
    suspend fun topMovie(): BaseResponse<SearchContentListResponseDto>
}