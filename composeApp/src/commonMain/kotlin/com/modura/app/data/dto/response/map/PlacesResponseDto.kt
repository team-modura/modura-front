package com.modura.app.data.dto.response.map

import com.modura.app.domain.model.response.map.PlaceResponseModel
import com.modura.app.domain.model.response.map.PlacesResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class PlacesResponseDto(
    val placeList: List<PlaceResponseDto>
){
    fun toPlacesResponseModel() = PlacesResponseModel(placeList.map { it.toPlaceResponseModel() })
}
