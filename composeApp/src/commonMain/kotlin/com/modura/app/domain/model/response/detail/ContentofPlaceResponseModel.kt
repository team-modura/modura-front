package com.modura.app.domain.model.response.detail

import com.modura.app.domain.model.response.search.SearchContentResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class ContentofPlaceResponseModel(
    val contentId : Int,
    val title:String,
    val thumbnail:String,
    val isLiked: Boolean
)