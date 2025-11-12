package com.modura.app.data.datasource

import com.modura.app.data.dto.response.detail.DetailResponseDto
import com.modura.app.data.dto.response.search.SearchResponseDto
import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto

interface SearchDataSource {
    suspend fun searchContents(query: String): List<List<SearchResponseDto>>
    suspend fun searchPlaces(query: String): List<List<SearchResponseDto>>

}
