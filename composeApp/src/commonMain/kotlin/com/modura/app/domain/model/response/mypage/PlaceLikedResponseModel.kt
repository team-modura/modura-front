package com.modura.app.domain.model.response.mypage

import kotlinx.serialization.Serializable

@Serializable
data class PlaceLikedResponseModel(
    val id: Int,
    val name: String,
    val isLiked: Boolean,
    val thumbnail: String?
)