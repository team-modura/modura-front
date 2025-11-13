package com.modura.app.domain.model.response.mypage

import kotlinx.serialization.Serializable

@Serializable
data class PlacesLikedResponseModel(
    val placeList: List<PlaceLikedResponseModel> = emptyList()
)