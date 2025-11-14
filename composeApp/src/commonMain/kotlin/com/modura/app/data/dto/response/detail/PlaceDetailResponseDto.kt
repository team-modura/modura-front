package com.modura.app.data.dto.response.detail

import com.modura.app.domain.model.response.detail.ContentDetailResponseModel
import com.modura.app.domain.model.response.detail.PlaceDetailResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetailResponseDto(
    val placeId : Int,
    val name:String,
    val reviewAvg: Double,
    val reviewCount: Int,
    val latitude: Double,
    val longitude: Double,
    val isLiked: Boolean,
    val placeImageUrl: String,
    val contentList: List<ContentofPlaceResponseDto?>,
    val reviews: List<ReviewofPlaceResponseDto?>
){
    fun toPlaceDetailResponseModel()= PlaceDetailResponseModel(placeId, name, reviewAvg, reviewCount, latitude, longitude, isLiked, placeImageUrl, contentList.map { it?.toContentofPlaceResponseModel() }, reviews.map { it?.toReviewofPlaceResponseModel() })
}
