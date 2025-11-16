package com.modura.app.data.dto.response.search

import com.modura.app.domain.model.response.search.SearchContentResponseModel
import com.modura.app.domain.model.response.search.SearchPlaceResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class SearchPlaceResponseDto(
    val id : Int,
    val name:String,
    val isLiked: Boolean,
    val thumbnail:String?
){
    fun toSearchPlaceResponseModel()= SearchPlaceResponseModel(id, name, isLiked, thumbnail)
}