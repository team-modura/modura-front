package com.modura.app.domain.model.response.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchPlaceResponseModel(
    val id : Int,
    val name:String,
    val isLiked: Boolean,
    val thumbnail:String?
)