package com.modura.app.data.dto.response.search

import com.modura.app.domain.model.response.search.SearchContentListResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class SearchContentListResponseDto(
    val contentList : List<SearchContentResponseDto> = emptyList()
){
    fun toSearchContentListResponseModel()= SearchContentListResponseModel(contentList.map { it.toSearchContentResponseModel() })
}