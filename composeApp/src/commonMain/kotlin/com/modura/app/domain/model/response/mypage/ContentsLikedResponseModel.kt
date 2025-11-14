package com.modura.app.domain.model.response.mypage

import kotlinx.serialization.Serializable

@Serializable
data class ContentsLikedResponseModel(
    val contentList: List<ContentLikedResponseModel> = emptyList()
)