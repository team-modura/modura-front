package com.modura.app.data.dto.response.search

import com.modura.app.domain.model.response.search.SearchContentListResponseModel
import com.modura.app.domain.model.response.search.SearchPopularResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class SearchPopularResponseDto(
    val keywords: List<String>
){
    fun toSearchPopularResponseModel() = SearchPopularResponseModel(keywords)
}