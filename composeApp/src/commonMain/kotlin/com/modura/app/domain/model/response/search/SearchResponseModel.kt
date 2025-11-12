package com.modura.app.domain.model.response.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseModel(
    val id : Int,
    val title:String,
    val isLiked: Boolean,
    val thumbnail:String
)