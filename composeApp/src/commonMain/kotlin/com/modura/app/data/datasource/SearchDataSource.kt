package com.modura.app.data.datasource

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.detail.DetailResponseDto
import com.modura.app.data.dto.response.search.SearchListResponseDto
import com.modura.app.data.dto.response.search.SearchResponseDto
import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto

interface SearchDataSource {
    suspend fun searchContents(query: String): BaseResponse<SearchListResponseDto>
    suspend fun searchPlaces(query: String): BaseResponse<SearchListResponseDto>

}
