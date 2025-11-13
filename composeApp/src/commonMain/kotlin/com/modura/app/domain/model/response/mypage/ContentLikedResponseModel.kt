package com.modura.app.domain.model.response.mypage

import kotlinx.serialization.Serializable

@Serializable
data class ContentLikedResponseModel(
    val id: Int,
    val title: String,
    val isLiked: Boolean,
    val thumbnail: String?
)