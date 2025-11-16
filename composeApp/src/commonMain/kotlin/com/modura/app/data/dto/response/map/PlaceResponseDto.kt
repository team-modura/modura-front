package com.modura.app.data.dto.response.map

import com.modura.app.domain.model.response.map.PlaceResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class PlaceResponseDto(
    val id: Int,
    val name: String,
    val isLiked: Boolean,
    val thumbnail: String?,
    val rating: Double,
    val reviewCount: Int,
    val latitude: Double,
    val longitude: Double,
    val content: List<String>
){
    fun toPlaceResponseModel() = PlaceResponseModel(id, name, isLiked, thumbnail, rating, reviewCount, latitude, longitude, content)
}
