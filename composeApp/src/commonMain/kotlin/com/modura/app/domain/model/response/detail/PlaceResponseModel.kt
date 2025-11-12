package com.modura.app.domain.model.response.detail

import kotlinx.serialization.Serializable

@Serializable
data class PlaceResponseModel(
    val id: Int,
    val name: String,
    val thumbnail: String,
    val isLiked: Boolean
)
