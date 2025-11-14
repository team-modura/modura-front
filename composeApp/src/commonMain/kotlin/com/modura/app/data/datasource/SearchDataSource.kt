package com.modura.app.data.datasource

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.search.SearchContentListResponseDto
import com.modura.app.data.dto.response.search.SearchPlaceListResponseDto

interface SearchDataSource {
    suspend fun searchContents(query: String): BaseResponse<SearchContentListResponseDto>
    suspend fun searchPlaces(query: String): BaseResponse<SearchPlaceListResponseDto>

}
