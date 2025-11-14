package com.modura.app.data.dto.response.mypage

import com.modura.app.domain.model.response.mypage.ContentLikedResponseModel
import com.modura.app.domain.model.response.mypage.ContentsLikedResponseModel
import com.modura.app.domain.model.response.mypage.PlacesLikedResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class PlacesLikedResponseDto(
    val placeList: List<PlaceLikedResponseDto> = emptyList()
){
    fun toPlacesLikedResponseModel() = PlacesLikedResponseModel(placeList.map { it.toPlaceLikedResponseModel() })
}