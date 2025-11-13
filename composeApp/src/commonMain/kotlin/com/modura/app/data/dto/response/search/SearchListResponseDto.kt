package com.modura.app.data.dto.response.search

import com.modura.app.domain.model.response.search.SearchListResponseModel
import com.modura.app.domain.model.response.search.SearchResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class SearchListResponseDto(
    val contentList : List<SearchResponseDto> = emptyList()
){
    fun toSearchListResponseModel()= SearchListResponseModel(contentList.map { it.toSearchResponseModel() })
}