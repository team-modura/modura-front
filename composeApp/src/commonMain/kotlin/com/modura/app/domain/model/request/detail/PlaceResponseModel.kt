package com.modura.app.domain.model.request.detail

data class PlaceResponseModel(
    val id: Int,
    val name: String,
    val thumbnail: String,
    val isLiked: Boolean
)
