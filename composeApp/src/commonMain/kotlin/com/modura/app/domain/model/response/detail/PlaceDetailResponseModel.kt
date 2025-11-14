package com.modura.app.domain.model.response.detail

import com.modura.app.data.dto.response.detail.ContentofPlaceResponseDto
import com.modura.app.data.dto.response.detail.ReviewofPlaceResponseDto
import com.modura.app.domain.model.response.detail.ContentDetailResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetailResponseModel(
    val placeId : Int,
    val name:String,
    val reviewAvg: Double,
    val reviewCount: Int,
    val latitude: Double,
    val longitude: Double,
    val isLiked: Boolean,
    val placeImageUrl: String,
    val contentList: List<ContentofPlaceResponseModel?>,
    val reviews: List<ReviewofPlaceResponseModel?>
)