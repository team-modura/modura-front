package com.modura.app.domain.model.request.detail

import com.modura.app.data.dto.request.detail.ContentReviewRequestDto
import com.modura.app.data.dto.request.detail.PlaceReviewRequestDto
import kotlinx.serialization.Serializable

@Serializable
data class PlaceReviewRequestModel(
    val rating: Int,
    val comment: String,
    val imageUrl: List<String?>
){
    fun toPlaceReviewRequestDto() = PlaceReviewRequestDto(rating, comment,imageUrl)
}
