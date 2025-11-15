package com.modura.app.data.dto.response.search

import com.modura.app.domain.model.response.search.SearchContentResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class SearchContentResponseDto(
    val id : Int,
    val title:String,
    val isLiked: Boolean,
    val thumbnail:String?
){
    fun toSearchContentResponseModel()= SearchContentResponseModel(id, title, isLiked, thumbnail)
}