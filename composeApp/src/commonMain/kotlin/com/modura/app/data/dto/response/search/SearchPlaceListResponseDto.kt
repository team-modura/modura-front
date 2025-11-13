package com.modura.app.data.dto.response.search

import com.modura.app.domain.model.response.search.SearchPlaceListResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class SearchPlaceListResponseDto(
    val placeList : List<SearchPlaceResponseDto> = emptyList()
){
    fun toSearchPlaceListResponseModel()= SearchPlaceListResponseModel(placeList.map { it.toSearchPlaceResponseModel() })
}