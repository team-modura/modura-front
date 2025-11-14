package com.modura.app.data.dto.response.detail

import com.modura.app.domain.model.response.detail.ContentofPlaceResponseModel
import com.modura.app.domain.model.response.search.SearchContentResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class ContentofPlaceResponseDto(
    val contentId : Int,
    val title:String,
    val thumbnail:String,
    val isLiked: Boolean
){
    fun toContentofPlaceResponseModel()= ContentofPlaceResponseModel(contentId, title, thumbnail, isLiked)
}