package com.modura.app.data.dto.response.search

import com.modura.app.domain.model.response.search.SearchResponseModel

data class SearchResponseDto(
    val id : Int,
    val title:String,
    val isLiked: Boolean,
    val thumbnail:String
){
    fun toSearchResponseModel()= SearchResponseModel(id, title, isLiked, thumbnail)
}